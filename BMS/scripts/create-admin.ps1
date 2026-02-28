# Create Admin User Script

$NewProjectId = "qmznlttogejdbcnrxggt"
$Query = @"
DO `$block`
BEGIN
    -- 1. Create the user in auth.users
    INSERT INTO auth.users (
        instance_id,
        id,
        aud,
        role,
        email,
        encrypted_password,
        email_confirmed_at,
        created_at,
        updated_at,
        raw_app_meta_data,
        raw_user_meta_data,
        is_super_admin
    ) VALUES (
        '00000000-0000-0000-0000-000000000000',
        gen_random_uuid(),
        'authenticated',
        'authenticated',
        'akshat980jain@gmail.com',
        crypt('Akshat@123', gen_salt('bf')),
        now(),
        now(),
        now(),
        '{"provider":"email","providers":["email"]}',
        '{"full_name":"Akshat Jain"}',
        FALSE
    )
    ON CONFLICT (email) DO NOTHING;

    -- 2. Force the role to be admin in public.user_roles
    INSERT INTO public.user_roles (user_id, role)
    SELECT id, 'admin' 
    FROM auth.users 
    WHERE email = 'akshat980jain@gmail.com'
    ON CONFLICT (user_id) DO UPDATE SET role = 'admin';

    -- Note: The handle_new_user trigger will automatically create the public.profiles record
END `$block`;
"@

Write-Host "Creating Admin User (akshat980jain@gmail.com) directly via database SQL..." -ForegroundColor Yellow
npx -y supabase db query $Query --project-ref $NewProjectId
