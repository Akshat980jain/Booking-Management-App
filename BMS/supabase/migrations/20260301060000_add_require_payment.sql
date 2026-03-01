-- Add require_payment column to provider_profiles
ALTER TABLE public.provider_profiles
ADD COLUMN IF NOT EXISTS require_payment BOOLEAN DEFAULT true;

COMMENT ON COLUMN public.provider_profiles.require_payment
IS 'When false, the Pay Now button is hidden and appointments are treated as free regardless of consultation_fee';

-- Update the provider_public_info view to include require_payment
CREATE OR REPLACE VIEW public.provider_public_info
WITH (security_invoker = true) AS
SELECT 
  p.user_id,
  p.full_name,
  p.avatar_url,
  p.city,
  p.country,
  pp.id as provider_id,
  pp.profession,
  pp.specialty,
  pp.bio,
  pp.location,
  pp.consultation_fee,
  pp.video_consultation_fee,
  pp.years_of_experience,
  pp.average_rating,
  pp.total_reviews,
  pp.is_verified,
  pp.video_enabled,
  pp.require_payment
FROM profiles p
JOIN provider_profiles pp ON p.user_id = pp.user_id
WHERE pp.is_approved = true AND pp.is_active = true;

-- Update the provider_profiles_public view to include require_payment
CREATE OR REPLACE VIEW public.provider_profiles_public
WITH (security_invoker = true) AS
SELECT 
  id,
  user_id,
  profession,
  specialty,
  bio,
  location,
  consultation_fee,
  video_consultation_fee,
  years_of_experience,
  average_rating,
  total_reviews,
  is_verified,
  video_enabled,
  is_approved,
  is_active,
  buffer_time_before,
  buffer_time_after,
  require_video_payment,
  require_payment,
  timezone,
  created_at
FROM provider_profiles
WHERE is_approved = true AND is_active = true;
