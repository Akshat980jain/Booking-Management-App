import { createClient } from '@supabase/supabase-js'

// Use the service_role key to execute DDL statements
const supabaseUrl = 'https://qmznlttogejdbcnrxggt.supabase.co'
// We need the service_role key for admin operations
// Let's try using the anon key first to check if the policy already exists
const supabaseKey = 'sb_publishable_-LyCXgxL8G6yrQaBy_jsCw_wSC4j7M_'

const supabase = createClient(supabaseUrl, supabaseKey)

async function test() {
    // Sign in as the user who sees the issue (we'll simulate as authenticated)
    const { data: authData, error: authErr } = await supabase.auth.signInWithPassword({
        email: 'akshat980jain@gmail.com',
        password: 'Akshat@123'
    })
    console.log('Auth:', authErr ? authErr.message : `signed in as ${authData.user?.email}`)

    // Test: Can this user see the provider_public_info?
    const { data, error } = await supabase
        .from('provider_public_info')
        .select('provider_id, full_name, profession')
    console.log('\nprovider_public_info (authenticated):')
    console.log('Error:', error?.message || 'none')
    console.log('Data:', JSON.stringify(data, null, 2))
    console.log('Count:', data?.length || 0)

    // Test: Can they query the profiles table directly?
    const { data: profiles, error: profErr } = await supabase
        .from('profiles')
        .select('user_id, full_name')
    console.log('\nprofiles (authenticated):')
    console.log('Error:', profErr?.message || 'none')
    console.log('Count:', profiles?.length || 0)
    console.log('Data:', JSON.stringify(profiles, null, 2))

    // Test: What about provider_profiles table?
    const { data: pp, error: ppErr } = await supabase
        .from('provider_profiles')
        .select('id, user_id, profession, is_approved, is_active')
    console.log('\nprovider_profiles (authenticated):')
    console.log('Error:', ppErr?.message || 'none')
    console.log('Count:', pp?.length || 0)
    console.log('Data:', JSON.stringify(pp, null, 2))
}

test()
