UPDATE public.provider_profiles
SET is_approved = true, is_active = true
WHERE is_approved = false OR is_approved IS NULL;

UPDATE public.approval_requests
SET status = 'approved', admin_notes = 'Auto-approved by system update'
WHERE status = 'pending';
