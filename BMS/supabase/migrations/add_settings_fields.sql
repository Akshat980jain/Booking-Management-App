-- =================================================================
-- PASTE THIS INTO: Supabase Dashboard → SQL Editor → Run
-- =================================================================
-- Adds missing settings fields to the profiles table
-- =================================================================

-- 1. Add preferred currency
ALTER TABLE public.profiles 
ADD COLUMN IF NOT EXISTS preferred_currency TEXT DEFAULT 'USD';

-- 2. Add session timeout (in minutes)
ALTER TABLE public.profiles 
ADD COLUMN IF NOT EXISTS session_timeout_minutes INTEGER DEFAULT 15;

-- 3. Ensure preferred_language has a default
ALTER TABLE public.profiles 
ALTER COLUMN preferred_language SET DEFAULT 'en-US';

-- 4. Create an index for faster lookups
CREATE INDEX IF NOT EXISTS idx_profiles_preferences 
ON public.profiles(preferred_language, preferred_currency);

-- DONE ✓
SELECT 'Migration successful: Settings fields added to profiles table.' AS result;
