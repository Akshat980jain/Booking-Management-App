import { createClient } from '@supabase/supabase-js'

const supabaseUrl = process.env.VITE_SUPABASE_URL || 'https://qmznlttogejdbcnrxggt.supabase.co'
const supabaseKey = process.env.VITE_SUPABASE_PUBLISHABLE_KEY || 'sb_publishable_-LyCXgxL8G6yrQaBy_jsCw_wSC4j7M_'

const supabase = createClient(supabaseUrl, supabaseKey)

async function testQuery() {
    console.log('Querying provider_profiles...')
    const { data: profiles, error: profileErr } = await supabase
        .from('provider_profiles')
        .select('id, user_id, is_approved, is_active')

    if (profileErr) console.error('Provider Profiles Error:', profileErr)
    else console.log('Provider Profiles:', profiles)

    console.log('\nQuerying provider_public_info view...')
    const { data: view, error: viewErr } = await supabase
        .from('provider_public_info')
        .select('*')

    if (viewErr) console.error('View Error:', viewErr)
    else console.log('View Data:', view)
}

testQuery()
