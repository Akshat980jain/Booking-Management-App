-- =================================================================
-- MIGRATION: Chat Message → Notification Trigger
-- =================================================================
-- Creates a PostgreSQL trigger that fires AFTER INSERT on chat_messages
-- and automatically inserts a notification row for the receiver.
--
-- This feeds the existing Supabase Realtime → Android notification
-- pipeline, so User B gets instant in-app notifications when User A
-- sends a message.
--
-- INSTRUCTIONS:
--   Paste this into Supabase Dashboard → SQL Editor → New Query → Run
-- =================================================================

-- 1. Create the trigger function
CREATE OR REPLACE FUNCTION public.notify_on_new_chat_message()
RETURNS TRIGGER AS $$
DECLARE
  v_receiver_id UUID;
  v_sender_name TEXT;
  v_p1 UUID;
  v_p2 UUID;
BEGIN
  -- Get participants from the conversation
  SELECT participant_1, participant_2
    INTO v_p1, v_p2
    FROM public.chat_conversations
   WHERE id = NEW.conversation_id;

  -- Determine who should receive the notification (the one who didn't send)
  IF NEW.sender_id = v_p1 THEN
    v_receiver_id := v_p2;
  ELSIF NEW.sender_id = v_p2 THEN
    v_receiver_id := v_p1;
  ELSE
    -- Fallback: try legacy user_id / provider_id columns
    DECLARE
      v_conv RECORD;
    BEGIN
      SELECT user_id, provider_id
        INTO v_conv
        FROM public.chat_conversations
       WHERE id = NEW.conversation_id;

      IF NEW.sender_id = v_conv.user_id THEN
        -- Sender is the user, receiver is the provider's auth user
        SELECT pp.user_id INTO v_receiver_id
          FROM public.provider_profiles pp
         WHERE pp.id = v_conv.provider_id;
      ELSE
        -- Sender is the provider, receiver is the user
        v_receiver_id := v_conv.user_id;
      END IF;
    END;
  END IF;

  -- Skip if receiver couldn't be determined
  IF v_receiver_id IS NULL THEN
    RETURN NEW;
  END IF;

  -- Get sender's display name
  SELECT COALESCE(full_name, 'Someone')
    INTO v_sender_name
    FROM public.profiles
   WHERE user_id = NEW.sender_id;

  -- If name not found, use a fallback
  IF v_sender_name IS NULL THEN
    v_sender_name := 'Someone';
  END IF;

  -- Insert notification for the receiver
  INSERT INTO public.notifications (user_id, title, message, type)
  VALUES (
    v_receiver_id,
    '💬 ' || v_sender_name,
    LEFT(NEW.message, 100),
    'contact_message'
  );

  RETURN NEW;
END;
$$ LANGUAGE plpgsql SECURITY DEFINER SET search_path = public;

-- 2. Drop the trigger if it already exists (idempotent)
DROP TRIGGER IF EXISTS tr_notify_on_chat_message ON public.chat_messages;

-- 3. Create the trigger
CREATE TRIGGER tr_notify_on_chat_message
  AFTER INSERT ON public.chat_messages
  FOR EACH ROW
  EXECUTE FUNCTION public.notify_on_new_chat_message();

-- Done ✓
SELECT 'Chat notification trigger created successfully!' AS result;
