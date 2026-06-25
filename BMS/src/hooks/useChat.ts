import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { supabase } from "@/integrations/supabase/client";
import { useAuth } from "@/contexts/AuthContext";
import { useToast } from "@/hooks/use-toast";
import { useEffect, useState } from "react";

export interface ChatMessage {
  id: string;
  conversation_id: string;
  sender_id: string;
  message: string;
  is_read: boolean;
  created_at: string;
}

export interface ChatConversation {
  id: string;
  user_id?: string | null;
  provider_id?: string | null;
  participant_1?: string | null;
  participant_2?: string | null;
  last_message_at: string;
  created_at: string;
  provider?: {
    id: string;
    profession: string;
    user_id: string;
    profile?: {
      full_name: string;
      avatar_url: string | null;
    };
  };
  user?: {
    full_name: string;
    avatar_url: string | null;
  };
  lastMessage?: ChatMessage;
  unreadCount?: number;
}

// Fix #7: Batch all enrichment queries instead of N+1
export const useChat = () => {
  const { user } = useAuth();
  const { toast } = useToast();
  const queryClient = useQueryClient();

  const { data: conversations = [], isLoading: isLoadingConversations } = useQuery({
    queryKey: ["chat-conversations", user?.id],
    queryFn: async () => {
      if (!user?.id) return [];

      // Fetch conversations first without the provider join (which fails due to missing FK in DB)
      const { data, error } = await supabase
        .from("chat_conversations")
        .select(`
          id, user_id, provider_id, participant_1, participant_2, last_message_at, created_at
        `)
        .order("last_message_at", { ascending: false })
        .limit(100);

      if (error) throw error;
      if (!data || data.length === 0) return [];

      // Fetch provider profiles separately
      const providerIds = data.map((c) => c.provider_id).filter(Boolean);
      let providerProfiles: any[] = [];
      if (providerIds.length > 0) {
        const { data: ppData, error: ppError } = await supabase
          .from("provider_profiles")
          .select("id, profession, user_id")
          .in("id", providerIds);
        if (ppError) throw ppError;
        providerProfiles = ppData || [];
      }
      const providerProfileMap = new Map(providerProfiles.map((p) => [p.id, p]));

      // Collect all user_ids we need profiles for (batch)
      const allUserIds = new Set<string>();
      const conversationIds: string[] = [];

      // Enrich conversations with provider profile in memory
      const enrichedConversations = data.map((conv) => {
        const providerProfile = conv.provider_id ? providerProfileMap.get(conv.provider_id) : undefined;
        return {
          ...conv,
          provider: providerProfile ? {
            id: providerProfile.id,
            profession: providerProfile.profession,
            user_id: providerProfile.user_id
          } : undefined
        };
      });

      enrichedConversations.forEach((conv: any) => {
        if (conv.user_id) allUserIds.add(conv.user_id);
        if (conv.provider?.user_id) allUserIds.add(conv.provider.user_id);
        if (conv.participant_1) allUserIds.add(conv.participant_1);
        if (conv.participant_2) allUserIds.add(conv.participant_2);
        conversationIds.push(conv.id);
      });

      console.log("[useChat] allUserIds:", Array.from(allUserIds));

      // Batch fetch all profiles at once
      const { data: profiles, error: profilesError } = await supabase
        .from("profiles")
        .select("user_id, full_name, avatar_url")
        .in("user_id", Array.from(allUserIds));

      if (profilesError) {
        console.error("[useChat] profilesError:", profilesError);
      }
      console.log("[useChat] fetched profiles:", profiles);

      const profileMap = new Map(profiles?.map(p => [p.user_id, p]) || []);
      console.log("[useChat] profileMap:", Array.from(profileMap.entries()));

      // Batch fetch last messages for all conversations using a single query
      // We'll get the latest message per conversation by fetching recent messages and deduping
      const { data: recentMessages } = await supabase
        .from("chat_messages")
        .select("id, conversation_id, sender_id, message, is_read, created_at")
        .in("conversation_id", conversationIds)
        .order("created_at", { ascending: false })
        .limit(500);

      const lastMessageMap = new Map<string, ChatMessage>();
      const unreadCountMap = new Map<string, number>();

      // Initialize unread counts
      conversationIds.forEach(id => unreadCountMap.set(id, 0));

      (recentMessages || []).forEach((msg) => {
        // Track last message per conversation
        if (!lastMessageMap.has(msg.conversation_id)) {
          lastMessageMap.set(msg.conversation_id, msg as ChatMessage);
        }
        // Count unread messages not from current user
        if (!msg.is_read && msg.sender_id !== user.id) {
          unreadCountMap.set(msg.conversation_id, (unreadCountMap.get(msg.conversation_id) || 0) + 1);
        }
      });

      const finalConversations = enrichedConversations.map((conv: any) => {
        // Find other user ID
        let otherUserId: string | null = null;
        if (conv.participant_1 && conv.participant_2) {
          otherUserId = conv.participant_1 === user.id ? conv.participant_2 : conv.participant_1;
        } else {
          // Fallback to legacy fields
          if (conv.user_id === user.id) {
            otherUserId = conv.provider?.user_id || null;
          } else {
            otherUserId = conv.user_id;
          }
        }

        const otherProfile = otherUserId ? (profileMap.get(otherUserId) || null) : null;
        const userProfile = conv.user_id ? (profileMap.get(conv.user_id) || null) : null;
        const providerUserProfile = conv.provider?.user_id ? (profileMap.get(conv.provider.user_id) || null) : null;

        return {
          ...conv,
          provider: conv.provider ? {
            ...conv.provider,
            profile: providerUserProfile || otherProfile,
          } : (otherProfile ? {
            id: "",
            profession: "",
            user_id: otherUserId || "",
            profile: otherProfile,
          } : undefined),
          user: userProfile || otherProfile,
          lastMessage: lastMessageMap.get(conv.id) || null,
          unreadCount: unreadCountMap.get(conv.id) || 0,
        };
      }) as ChatConversation[];

      console.log("[useChat] finalConversations:", finalConversations);
      return finalConversations;
    },
    enabled: !!user?.id,
  });

  // Fix #15: Check for existing conversation before creating (dedup already exists)
  const createConversationMutation = useMutation({
    mutationFn: async (providerId: string) => {
      if (!user?.id) throw new Error("Must be logged in");

      // 1. Fetch provider's user_id from provider_profiles
      const { data: providerProfile, error: ppError } = await supabase
        .from("provider_profiles")
        .select("user_id")
        .eq("id", providerId)
        .single();

      if (ppError || !providerProfile?.user_id) {
        throw new Error(ppError?.message || "Provider user profile not found");
      }

      // 2. Call the RPC function to get or create the conversation
      const { data: conversationId, error: rpcError } = await supabase
        .rpc("get_or_create_conversation", {
          p_user_1: user.id,
          p_user_2: providerProfile.user_id,
        });

      if (rpcError) throw rpcError;
      return conversationId;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["chat-conversations"] });
    },
    onError: (error: Error) => {
      toast({
        title: "Error",
        description: error.message,
        variant: "destructive",
      });
    },
  });

  return {
    conversations,
    isLoadingConversations,
    createConversation: createConversationMutation.mutateAsync,
  };
};

export const useChatMessages = (conversationId: string | null) => {
  const { user } = useAuth();
  const { toast } = useToast();
  const queryClient = useQueryClient();
  const [realtimeMessages, setRealtimeMessages] = useState<ChatMessage[]>([]);

  const { data: messages = [], isLoading } = useQuery({
    queryKey: ["chat-messages", conversationId],
    queryFn: async () => {
      if (!conversationId) return [];

      const { data, error } = await supabase
        .from("chat_messages")
        .select("id, conversation_id, sender_id, message, is_read, created_at")
        .eq("conversation_id", conversationId)
        .order("created_at", { ascending: true })
        .limit(200);

      if (error) throw error;
      return data as ChatMessage[];
    },
    enabled: !!conversationId,
  });

  useEffect(() => {
    if (!conversationId) return;

    const channel = supabase
      .channel(`chat:${conversationId}`)
      .on(
        "postgres_changes",
        {
          event: "INSERT",
          schema: "public",
          table: "chat_messages",
          filter: `conversation_id=eq.${conversationId}`,
        },
        (payload) => {
          setRealtimeMessages((prev) => [...prev, payload.new as ChatMessage]);
          queryClient.invalidateQueries({ queryKey: ["chat-conversations"] });
        }
      )
      .subscribe();

    return () => {
      supabase.removeChannel(channel);
    };
  }, [conversationId, queryClient]);

  const allMessages = [...messages, ...realtimeMessages.filter(
    (rm) => !messages.some((m) => m.id === rm.id)
  )];

  const sendMessageMutation = useMutation({
    mutationFn: async (message: string) => {
      if (!user?.id || !conversationId) throw new Error("Invalid state");

      const { error } = await supabase.from("chat_messages").insert({
        conversation_id: conversationId,
        sender_id: user.id,
        message,
      });

      if (error) throw error;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["chat-messages", conversationId] });
    },
    onError: (error: Error) => {
      toast({
        title: "Error sending message",
        description: error.message,
        variant: "destructive",
      });
    },
  });

  const markAsReadMutation = useMutation({
    mutationFn: async () => {
      if (!user?.id || !conversationId) return;

      const { error } = await supabase
        .from("chat_messages")
        .update({ is_read: true })
        .eq("conversation_id", conversationId)
        .neq("sender_id", user.id)
        .eq("is_read", false);

      if (error) throw error;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["chat-conversations"] });
    },
  });

  return {
    messages: allMessages,
    isLoading,
    sendMessage: sendMessageMutation.mutate,
    markAsRead: markAsReadMutation.mutate,
    isSending: sendMessageMutation.isPending,
  };
};
