-- Fix: Add RLS policy for authenticated users to read provider profiles via the view
-- The provider_public_info view uses security_invoker=true which means
-- authenticated users need explicit permission to read OTHER users' profiles.
-- Currently only 'anon' and 'admin' can read provider profiles.
-- PostgreSQL OR's multiple SELECT policies, so this adds to existing own-profile policy.

CREATE POLICY "Authenticated can read provider profiles via view"
ON public.profiles FOR SELECT
TO authenticated
USING (
  user_id IN (
    SELECT pp.user_id FROM provider_profiles pp
    WHERE pp.is_approved = true AND pp.is_active = true
  )
);
