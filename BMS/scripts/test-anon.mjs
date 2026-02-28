import { createClient } from '@supabase/supabase-js'

const supabaseUrl = process.env.VITE_SUPABASE_URL || 'https://qmznlttogejdbcnrxggt.supabase.co'
const supabaseKey = process.env.VITE_SUPABASE_PUBLISHABLE_KEY || 'sb_publishable_-LyCXgxL8G6yrQaBy_jsCw_wSC4j7M_'

const supabase = createClient(supabaseUrl, supabaseKey)

async function testQuery() {
    console.log('Querying provider_public_info view with ANON key...')
    const { data: view, error: viewErr } = await supabase
        .from('provider_public_info')
        .select('*')

    if (viewErr) console.error('View Error:', viewErr)
    else console.log('View Data (length):', view?.length, 'Data:', view)
}

testQuery()
