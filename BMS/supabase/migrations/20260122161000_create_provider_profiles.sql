-- Create provider_profiles table (missing from original migrations)
-- MINIMAL: Only table creation + RLS enable. All policies and indexes 
-- are created by later migrations.

CREATE TABLE IF NOT EXISTS public.provider_profiles (
    id UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id UUID NOT NULL UNIQUE REFERENCES auth.users(id) ON DELETE CASCADE,
    profession TEXT NOT NULL DEFAULT '',
    specialty TEXT,
    bio TEXT,
    location TEXT,
    years_of_experience INTEGER DEFAULT 0,
    phone TEXT,
    phone_verified BOOLEAN DEFAULT false,
    sms_notifications_enabled BOOLEAN DEFAULT false,
    consultation_fee NUMERIC DEFAULT 0,
    video_consultation_fee NUMERIC,
    video_enabled BOOLEAN DEFAULT false,
    is_active BOOLEAN DEFAULT true,
    is_approved BOOLEAN DEFAULT false,
    is_verified BOOLEAN DEFAULT false,
    verification_type TEXT,
    verification_documents TEXT[],
    verified_at TIMESTAMP WITH TIME ZONE,
    buffer_time_before INTEGER DEFAULT 0,
    buffer_time_after INTEGER DEFAULT 15,
    timezone TEXT DEFAULT 'UTC',
    average_rating NUMERIC,
    total_reviews INTEGER DEFAULT 0,
    stripe_account_id TEXT,
    stripe_onboarding_complete BOOLEAN DEFAULT false,
    stripe_charges_enabled BOOLEAN DEFAULT false,
    stripe_payouts_enabled BOOLEAN DEFAULT false,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

ALTER TABLE public.provider_profiles ENABLE ROW LEVEL SECURITY;

-- Minimal policies needed before later migrations add more
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_policies WHERE tablename = 'provider_profiles' AND policyname = 'Public can view approved providers') THEN
    CREATE POLICY "Public can view approved providers" ON public.provider_profiles FOR SELECT USING (is_approved = true AND is_active = true);
  END IF;
  IF NOT EXISTS (SELECT 1 FROM pg_policies WHERE tablename = 'provider_profiles' AND policyname = 'Providers can view their own profile') THEN
    CREATE POLICY "Providers can view their own profile" ON public.provider_profiles FOR SELECT USING (auth.uid() = user_id);
  END IF;
  IF NOT EXISTS (SELECT 1 FROM pg_policies WHERE tablename = 'provider_profiles' AND policyname = 'Providers can insert their own profile') THEN
    CREATE POLICY "Providers can insert their own profile" ON public.provider_profiles FOR INSERT WITH CHECK (auth.uid() = user_id);
  END IF;
  IF NOT EXISTS (SELECT 1 FROM pg_policies WHERE tablename = 'provider_profiles' AND policyname = 'Providers can update their own profile') THEN
    CREATE POLICY "Providers can update their own profile" ON public.provider_profiles FOR UPDATE USING (auth.uid() = user_id);
  END IF;
  IF NOT EXISTS (SELECT 1 FROM pg_policies WHERE tablename = 'provider_profiles' AND policyname = 'Admins can view all provider profiles') THEN
    CREATE POLICY "Admins can view all provider profiles" ON public.provider_profiles FOR SELECT USING (has_role(auth.uid(), 'admin'));
  END IF;
  IF NOT EXISTS (SELECT 1 FROM pg_policies WHERE tablename = 'provider_profiles' AND policyname = 'Admins can update any provider profile') THEN
    CREATE POLICY "Admins can update any provider profile" ON public.provider_profiles FOR UPDATE USING (has_role(auth.uid(), 'admin'));
  END IF;
END $$;

-- Trigger for updated_at
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_trigger WHERE tgname = 'update_provider_profiles_updated_at') THEN
    CREATE TRIGGER update_provider_profiles_updated_at
      BEFORE UPDATE ON public.provider_profiles
      FOR EACH ROW
      EXECUTE FUNCTION public.update_updated_at_column();
  END IF;
END $$;
