import { useQuery, useInfiniteQuery } from "@tanstack/react-query";
import { supabase } from "@/integrations/supabase/client";

export interface Provider {
  id: string;
  user_id: string;
  profession: string;
  specialty: string | null;
  bio: string | null;
  consultation_fee: number | null;
  location: string | null;
  years_of_experience: number | null;
  is_approved: boolean;
  is_active: boolean;
  average_rating: number | null;
  total_reviews: number | null;
  video_enabled: boolean | null;
  video_consultation_fee: number | null;
  is_verified: boolean | null;
  require_payment: boolean | null;
  profile?: {
    full_name: string;
    avatar_url: string | null;
  };
}

const PAGE_SIZE = 12;

// Map category buttons to profession search patterns
const categoryToProfessionPatterns: Record<string, string[]> = {
  doctor: ["doctor", "physician", "cardiologist", "dermatologist", "pediatrician", "general", "surgeon", "dentist", "psychiatrist", "neurologist", "oncologist", "orthopedic", "gynecologist", "urologist", "ent", "radiologist", "pathologist", "anesthesiologist"],
  lawyer: ["lawyer", "attorney", "advocate", "legal", "counsel", "barrister", "solicitor"],
  barber: ["barber", "hairdresser", "hair stylist"],
  therapist: ["therapist", "psychologist", "counselor", "counsellor", "mental health"],
  consultant: ["consultant", "advisor", "adviser", "business consultant", "financial advisor", "management"],
  stylist: ["stylist", "fashion", "makeup", "beauty", "cosmetologist", "aesthetician"],
};

// Helper to map provider_public_info rows to Provider shape
const mapPublicInfoToProvider = (row: Record<string, unknown>): Provider => ({
  id: row.provider_id as string,
  user_id: row.user_id as string,
  profession: row.profession as string,
  specialty: (row.specialty as string) || null,
  bio: (row.bio as string) || null,
  consultation_fee: (row.consultation_fee as number) || null,
  location: (row.location as string) || null,
  years_of_experience: (row.years_of_experience as number) || null,
  is_approved: true,
  is_active: true,
  average_rating: (row.average_rating as number) || null,
  total_reviews: (row.total_reviews as number) || null,
  video_enabled: (row.video_enabled as boolean) || null,
  video_consultation_fee: (row.video_consultation_fee as number) || null,
  is_verified: (row.is_verified as boolean) || null,
  require_payment: row.require_payment != null ? (row.require_payment as boolean) : true,
  profile: {
    full_name: (row.full_name as string) || "",
    avatar_url: (row.avatar_url as string) || null,
  },
});

export const useProviders = (category?: string, searchQuery?: string) => {
  return useQuery({
    queryKey: ["providers", category, searchQuery],
    queryFn: async (): Promise<Provider[]> => {
      // If category is "__none__", return empty array (no category selected yet)
      if (category === "__none__") {
        return [];
      }

      // Use the secure public view instead of raw tables
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const { data, error } = await (supabase as any)
        .from("provider_public_info")
        .select("provider_id, user_id, profession, specialty, bio, consultation_fee, location, years_of_experience, average_rating, total_reviews, video_enabled, video_consultation_fee, is_verified, require_payment, full_name, avatar_url, city, country")
        .limit(500);

      if (error) throw error;
      if (!data || data.length === 0) return [];

      // Map to Provider shape
      let providers: Provider[] = data.map(mapPublicInfoToProvider);

      // Filter by category if provided
      if (category && category !== "all") {
        const patterns = categoryToProfessionPatterns[category.toLowerCase()];
        if (patterns) {
          providers = providers.filter((provider: Provider) => {
            const profession = provider.profession?.toLowerCase() || "";
            const specialty = provider.specialty?.toLowerCase() || "";
            return patterns.some(pattern =>
              profession.includes(pattern) || specialty.includes(pattern)
            );
          });
        } else {
          const lowerCategory = category.toLowerCase();
          providers = providers.filter((provider: Provider) =>
            provider.profession?.toLowerCase().includes(lowerCategory) ||
            provider.specialty?.toLowerCase().includes(lowerCategory)
          );
        }
      }

      // Filter by search query if provided
      if (searchQuery) {
        const lowerQuery = searchQuery.toLowerCase();
        providers = providers.filter((provider: Provider) =>
          provider.profile?.full_name?.toLowerCase().includes(lowerQuery) ||
          provider.profession?.toLowerCase().includes(lowerQuery) ||
          provider.specialty?.toLowerCase().includes(lowerQuery)
        );
      }

      return providers;
    },
    staleTime: 60 * 1000,
  });
};

// Paginated version for large datasets
export const useProvidersPaginated = (category?: string, searchQuery?: string) => {
  return useInfiniteQuery({
    queryKey: ["providers-paginated", category, searchQuery],
    queryFn: async ({ pageParam = 0 }): Promise<{ providers: Provider[]; nextPage: number | null }> => {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      let query = (supabase as any)
        .from("provider_public_info")
        .select("provider_id, user_id, profession, specialty, bio, consultation_fee, location, years_of_experience, average_rating, total_reviews, video_enabled, video_consultation_fee, is_verified, require_payment, full_name, avatar_url, city, country", { count: "exact" })
        .range(pageParam * PAGE_SIZE, (pageParam + 1) * PAGE_SIZE - 1);

      if (category && category !== "all") {
        query = query.eq("profession", category);
      }

      const { data, error, count } = await query;

      if (error) throw error;

      if (!data || data.length === 0) {
        return { providers: [], nextPage: null };
      }

      let providers: Provider[] = data.map(mapPublicInfoToProvider);

      // Filter by search query if provided (client-side for paginated)
      if (searchQuery) {
        const lowerQuery = searchQuery.toLowerCase();
        providers = providers.filter((provider: Provider) =>
          provider.profile?.full_name?.toLowerCase().includes(lowerQuery) ||
          provider.profession?.toLowerCase().includes(lowerQuery) ||
          provider.specialty?.toLowerCase().includes(lowerQuery)
        );
      }

      const hasMore = count ? (pageParam + 1) * PAGE_SIZE < count : false;

      return {
        providers,
        nextPage: hasMore ? pageParam + 1 : null,
      };
    },
    getNextPageParam: (lastPage) => lastPage.nextPage,
    initialPageParam: 0,
    staleTime: 60 * 1000,
  });
};

export const useProvider = (providerId: string | undefined) => {
  return useQuery({
    queryKey: ["provider", providerId],
    queryFn: async (): Promise<Provider | null> => {
      if (!providerId) return null;

      // Try the secure public view first
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const { data, error } = await (supabase as any)
        .from("provider_public_info")
        .select("provider_id, user_id, profession, specialty, bio, consultation_fee, location, years_of_experience, average_rating, total_reviews, video_enabled, video_consultation_fee, is_verified, require_payment, full_name, avatar_url, city, country")
        .eq("provider_id", providerId)
        .maybeSingle();

      if (error) throw error;
      if (data) return mapPublicInfoToProvider(data);

      // Fallback: query provider_profiles directly if the view returned nothing
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const { data: ppData, error: ppError } = await (supabase as any)
        .from("provider_profiles")
        .select("id, user_id, profession, specialty, bio, consultation_fee, location, years_of_experience, average_rating, total_reviews, video_enabled, video_consultation_fee, is_verified, is_approved, is_active")
        .eq("id", providerId)
        .maybeSingle();

      if (ppError) throw ppError;
      if (!ppData) return null;

      // Fetch profile name/avatar separately
      let fullName = "";
      let avatarUrl: string | null = null;
      if (ppData.user_id) {
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        const { data: profileData } = await (supabase as any)
          .from("profiles")
          .select("full_name, avatar_url")
          .eq("user_id", ppData.user_id)
          .maybeSingle();
        if (profileData) {
          fullName = profileData.full_name || "";
          avatarUrl = profileData.avatar_url || null;
        }
      }

      return {
        id: ppData.id,
        user_id: ppData.user_id,
        profession: ppData.profession,
        specialty: ppData.specialty || null,
        bio: ppData.bio || null,
        consultation_fee: ppData.consultation_fee || null,
        location: ppData.location || null,
        years_of_experience: ppData.years_of_experience || null,
        is_approved: ppData.is_approved ?? false,
        is_active: ppData.is_active ?? false,
        average_rating: ppData.average_rating || null,
        total_reviews: ppData.total_reviews || null,
        video_enabled: ppData.video_enabled || null,
        video_consultation_fee: ppData.video_consultation_fee || null,
        is_verified: ppData.is_verified || null,
        require_payment: ppData.require_payment ?? true,
        profile: {
          full_name: fullName,
          avatar_url: avatarUrl,
        },
      };
    },
    enabled: !!providerId,
    staleTime: 60 * 1000,
    retry: 1,
  });
};
