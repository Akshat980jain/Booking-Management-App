import { createClient } from '@supabase/supabase-js'
import { writeFileSync } from 'fs'

const supabaseUrl = 'https://qmznlttogejdbcnrxggt.supabase.co'
const supabaseKey = 'sb_publishable_-LyCXgxL8G6yrQaBy_jsCw_wSC4j7M_'

const supabase = createClient(supabaseUrl, supabaseKey)
let output = ''
const log = (...args) => { output += args.join(' ') + '\n'; console.log(...args) }

async function diagnose() {
    // Sign in as the test user
    const { data: authData, error: authErr } = await supabase.auth.signInWithPassword({
        email: 'akshat980jain@gmail.com',
        password: 'Akshat@123'
    })
    log('Auth:', authErr ? authErr.message : `signed in as ${authData.user?.email}`)

    // Test 1: Get all providers to see their IDs
    log('\n=== All provider IDs ===')
    const { data: all, error: allErr } = await supabase
        .from('provider_public_info')
        .select('provider_id, user_id, profession, full_name')
    log('Error:', allErr?.message || 'none')
    log('Data:', JSON.stringify(all, null, 2))

    // Test 2: Query by specific provider_id (like the detail page does)
    if (all && all.length > 0) {
        const testId = all[0].provider_id
        log('\n=== Query by provider_id:', testId, '===')
        const { data: single, error: singleErr } = await supabase
            .from('provider_public_info')
            .select('provider_id, user_id, profession, specialty, bio, consultation_fee, location, years_of_experience, average_rating, total_reviews, video_enabled, video_consultation_fee, is_verified, full_name, avatar_url, city, country')
            .eq('provider_id', testId)
            .maybeSingle()
        log('Error:', singleErr?.message || 'none')
        log('Data:', JSON.stringify(single, null, 2))
    }

    // Test 3: Check the provider_profiles table directly
    log('\n=== provider_profiles table ===')
    const { data: pp, error: ppErr } = await supabase
        .from('provider_profiles')
        .select('id, user_id, profession')
    log('Error:', ppErr?.message || 'none')
    log('Data:', JSON.stringify(pp, null, 2))

    // Test 4: Try querying provider_profiles by id (which is what the view returns as provider_id)
    if (pp && pp.length > 0) {
        const testId = pp[0].id
        log('\n=== Query provider_profiles by id:', testId, '===')
        const { data: byId, error: byIdErr } = await supabase
            .from('provider_profiles')
            .select('id, user_id, profession, specialty, bio, consultation_fee, location, years_of_experience, average_rating, total_reviews, is_approved, is_active, video_enabled, video_consultation_fee, is_verified')
            .eq('id', testId)
            .maybeSingle()
        log('Error:', byIdErr?.message || 'none')
        log('Data:', JSON.stringify(byId, null, 2))
    }

    writeFileSync('scripts/diagnose-detail.txt', output)
    log('\nSaved to scripts/diagnose-detail.txt')
}

diagnose()
