import { createClient } from '@supabase/supabase-js'
import { writeFileSync } from 'fs'

const supabaseUrl = 'https://qmznlttogejdbcnrxggt.supabase.co'
const supabaseKey = 'sb_publishable_-LyCXgxL8G6yrQaBy_jsCw_wSC4j7M_'

const supabase = createClient(supabaseUrl, supabaseKey)
let output = ''
const log = (...args) => { output += args.join(' ') + '\n'; console.log(...args) }

async function diagnose() {
    // Test as authenticated user
    const { data: authData, error: authErr } = await supabase.auth.signInWithPassword({
        email: 'akshat980jain@gmail.com',
        password: 'Akshat@123'
    })
    log('Auth:', authErr ? authErr.message : `signed in as ${authData.user?.email}`)

    log('\n=== LAYER 1: provider_profiles ===')
    const { data: pp, error: ppErr } = await supabase
        .from('provider_profiles')
        .select('id, user_id, profession, specialty, is_approved, is_active')
    log('Error:', ppErr?.message || 'none')
    log('Count:', pp?.length || 0)
    log('Data:', JSON.stringify(pp, null, 2))

    log('\n=== LAYER 2: profiles for provider user_ids ===')
    if (pp && pp.length > 0) {
        const userIds = pp.map(p => p.user_id)
        const { data: profiles, error: profilesErr } = await supabase
            .from('profiles')
            .select('user_id, full_name, email, avatar_url, status')
            .in('user_id', userIds)
        log('Error:', profilesErr?.message || 'none')
        log('Count:', profiles?.length || 0)
        log('Data:', JSON.stringify(profiles, null, 2))
    }

    log('\n=== LAYER 3: provider_public_info view (authenticated) ===')
    const { data: view, error: viewErr } = await supabase
        .from('provider_public_info')
        .select('provider_id, user_id, profession, specialty, bio, consultation_fee, location, years_of_experience, average_rating, total_reviews, video_enabled, video_consultation_fee, is_verified, full_name, avatar_url, city, country')
        .limit(500)
    log('Error:', viewErr?.message || 'none')
    log('Count:', view?.length || 0)
    log('Data:', JSON.stringify(view, null, 2))

    log('\n=== LAYER 4: user_roles ===')
    const { data: roles, error: rolesErr } = await supabase
        .from('user_roles')
        .select('user_id, role')
    log('Error:', rolesErr?.message || 'none')
    log('Count:', roles?.length || 0)
    log('Data:', JSON.stringify(roles, null, 2))

    // Sign out and test as anonymous
    await supabase.auth.signOut()

    log('\n=== LAYER 5: provider_public_info as ANONYMOUS ===')
    const { data: anonView, error: anonViewErr } = await supabase
        .from('provider_public_info')
        .select('provider_id, user_id, profession, specialty, full_name')
        .limit(500)
    log('Error:', anonViewErr?.message || 'none')
    log('Count:', anonView?.length || 0)
    log('Data:', JSON.stringify(anonView, null, 2))

    log('\n=== LAYER 6: provider_profiles_public view ===')
    const { data: ppView, error: ppViewErr } = await supabase
        .from('provider_profiles_public')
        .select('*')
        .limit(500)
    log('Error:', ppViewErr?.message || 'none')
    log('Count:', ppView?.length || 0)
    log('Data:', JSON.stringify(ppView, null, 2))

    writeFileSync('scripts/diagnose-output.txt', output)
    log('\nOutput saved to scripts/diagnose-output.txt')
}

diagnose()
