# Fix Admin Role Script

$NewProjectId = "qmznlttogejdbcnrxggt"
$Query = @"
DO `$block`
DECLARE
    v_user_id UUID;
BEGIN
    -- 1. Get the user ID
    SELECT id INTO v_user_id FROM auth.users WHERE email = 'akshat980jain@gmail.com';
    
    IF v_user_id IS NOT NULL THEN
            -- 2. Update metadata to explicitly show admin role
            UPDATE auth.users 
            SET raw_user_meta_data = '{"full_name":"Akshat Jain","role":"admin"}'::jsonb 
            WHERE id = v_user_id;

            -- 3. Delete any 'user' or non-admin roles for this user to avoid conflicts
            DELETE FROM public.user_roles 
            WHERE user_id = v_user_id AND role != 'admin';

            -- 4. Ensure the 'admin' role exists
            INSERT INTO public.user_roles (user_id, role)
            VALUES (v_user_id, 'admin')
            ON CONFLICT (user_id, role) DO NOTHING;
            
            -- Also ensure the profile exists just in case
            INSERT INTO public.profiles (user_id, full_name, email)
            VALUES (v_user_id, 'Akshat Jain', 'akshat980jain@gmail.com')
            ON CONFLICT (user_id) DO NOTHING;
        END IF;
END `$block`;
"@

Write-Host "Fixing the Admin role for akshat980jain@gmail.com in the database..." -ForegroundColor Yellow
npx -y supabase db query $Query --project-ref $NewProjectId
Write-Host "Role successfully updated to 'admin'!" -ForegroundColor Green
