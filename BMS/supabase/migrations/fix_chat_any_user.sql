-- =================================================================
-- PASTE THIS ENTIRE SCRIPT INTO:
-- Supabase Dashboard → SQL Editor → New Query → Run
-- =================================================================
-- This fixes chat so ANY user can message ANY other user (no provider required)
-- =================================================================

-- STEP 1: Add new participant columns (safe - won't fail if already exist)
ALTER TABLE public.chat_conversations
  ADD COLUMN IF NOT EXISTS participant_1 UUID REFERENCES auth.users(id) ON DELETE CASCADE,
  ADD COLUMN IF NOT EXISTS participant_2 UUID REFERENCES auth.users(id) ON DELETE CASCADE,
  ADD COLUMN IF NOT EXISTS status TEXT DEFAULT 'active';

-- STEP 2: Backfill participant columns from existing user_id / provider_id data
UPDATE public.chat_conversations cc
SET
  participant_1 = CASE
    WHEN cc.user_id::TEXT < pp.user_id::TEXT THEN cc.user_id
    ELSE pp.user_id
  END,
  participant_2 = CASE
    WHEN cc.user_id::TEXT < pp.user_id::TEXT THEN pp.user_id
    ELSE cc.user_id
  END
FROM public.provider_profiles pp
WHERE pp.id = cc.provider_id
  AND cc.participant_1 IS NULL;

-- STEP 3: Make user_id nullable so new conversations don't need it
ALTER TABLE public.chat_conversations
  ALTER COLUMN user_id DROP NOT NULL;

-- STEP 4: Drop the FK on provider_id (the restrictive constraint)
ALTER TABLE public.chat_conversations
  DROP CONSTRAINT IF EXISTS chat_conversations_provider_id_fkey;

-- Make provider_id nullable too
ALTER TABLE public.chat_conversations
  ALTER COLUMN provider_id DROP NOT NULL;

-- STEP 5: Add unique constraint on participant pair (prevent duplicates)
ALTER TABLE public.chat_conversations
  DROP CONSTRAINT IF EXISTS chat_conversations_participant_pair_key;

ALTER TABLE public.chat_conversations
  ADD CONSTRAINT chat_conversations_participant_pair_key
  UNIQUE NULLS NOT DISTINCT (participant_1, participant_2);

-- STEP 6: Replace old RLS policies with open participant-based ones
DROP POLICY IF EXISTS "Users can view their conversations" ON public.chat_conversations;
DROP POLICY IF EXISTS "Providers can view their conversations" ON public.chat_conversations;
DROP POLICY IF EXISTS "Users can create conversations" ON public.chat_conversations;
DROP POLICY IF EXISTS "Users can update their conversations" ON public.chat_conversations;
DROP POLICY IF EXISTS "Providers can update their conversations" ON public.chat_conversations;

CREATE POLICY "Any user can view their conversations"
  ON public.chat_conversations FOR SELECT
  USING (
    auth.uid() = user_id
    OR auth.uid() IN (participant_1, participant_2)
    OR provider_id IN (SELECT id FROM public.provider_profiles WHERE user_id = auth.uid())
  );

CREATE POLICY "Any user can create conversations"
  ON public.chat_conversations FOR INSERT
  WITH CHECK (
    auth.uid() = user_id
    OR auth.uid() IN (participant_1, participant_2)
  );

CREATE POLICY "Any user can update their conversations"
  ON public.chat_conversations FOR UPDATE
  USING (
    auth.uid() = user_id
    OR auth.uid() IN (participant_1, participant_2)
    OR provider_id IN (SELECT id FROM public.provider_profiles WHERE user_id = auth.uid())
  );

-- STEP 7: Fix chat_messages RLS to also work with participant columns
DROP POLICY IF EXISTS "Users can view messages in their conversations" ON public.chat_messages;
DROP POLICY IF EXISTS "Users can send messages" ON public.chat_messages;
DROP POLICY IF EXISTS "Users can send messages in their conversations" ON public.chat_messages;

CREATE POLICY "Users can view messages in their conversations"
  ON public.chat_messages FOR SELECT
  USING (
    EXISTS (
      SELECT 1 FROM public.chat_conversations c
      WHERE c.id = conversation_id
        AND (
          auth.uid() = c.user_id
          OR auth.uid() IN (c.participant_1, c.participant_2)
          OR c.provider_id IN (SELECT id FROM public.provider_profiles WHERE user_id = auth.uid())
        )
    )
  );

CREATE POLICY "Users can send messages in their conversations"
  ON public.chat_messages FOR INSERT
  WITH CHECK (
    sender_id = auth.uid() AND
    EXISTS (
      SELECT 1 FROM public.chat_conversations c
      WHERE c.id = conversation_id
        AND (
          auth.uid() = c.user_id
          OR auth.uid() IN (c.participant_1, c.participant_2)
          OR c.provider_id IN (SELECT id FROM public.provider_profiles WHERE user_id = auth.uid())
        )
    )
  );

-- STEP 8: Create indexes for the new columns
CREATE INDEX IF NOT EXISTS idx_chat_conversations_p1 ON public.chat_conversations(participant_1);
CREATE INDEX IF NOT EXISTS idx_chat_conversations_p2 ON public.chat_conversations(participant_2);

-- DONE ✓
SELECT 'Migration complete! Any user can now message any other user.' AS result;
