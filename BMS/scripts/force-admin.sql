-- Find user and delete conflicting roles
DELETE FROM public.user_roles 
WHERE user_id = (SELECT id FROM auth.users WHERE email = 'akshat980jain@gmail.com');

-- Insert the admin role explicitly
INSERT INTO public.user_roles (user_id, role)
SELECT id, 'admin' 
FROM auth.users 
WHERE email = 'akshat980jain@gmail.com';

-- Update the user's raw metadata as the frontend might read this instead of the table!
UPDATE auth.users 
SET raw_user_meta_data = jsonb_set(COALESCE(raw_user_meta_data, '{}'::jsonb), '{role}', '"admin"')
WHERE email = 'akshat980jain@gmail.com';
