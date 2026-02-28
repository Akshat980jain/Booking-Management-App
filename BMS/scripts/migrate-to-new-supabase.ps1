###############################################################################
# BookEase24X7 — Supabase Backend Migration Script
# Migrates from: eqbtrurgizszxdwnhhlk → qmznlttogejdbcnrxggt
###############################################################################

param(
    [switch]$DryRun,
    [switch]$SkipFunctions,
    [switch]$SkipSecrets,
    [switch]$SkipSchema
)

# ─── Configuration ───────────────────────────────────────────────────────────
$OLD_PROJECT_ID = "eqbtrurgizszxdwnhhlk"
$NEW_PROJECT_ID = "qmznlttogejdbcnrxggt"
$NEW_SUPABASE_URL = "https://qmznlttogejdbcnrxggt.supabase.co"
$PROJECT_ROOT = Split-Path -Parent $PSScriptRoot

# ─── Colors & Helpers ────────────────────────────────────────────────────────
function Write-Phase { param($msg) Write-Host "`n═══════════════════════════════════════════════════════" -ForegroundColor Cyan; Write-Host "  $msg" -ForegroundColor Cyan; Write-Host "═══════════════════════════════════════════════════════" -ForegroundColor Cyan }
function Write-Step { param($msg) Write-Host "  → $msg" -ForegroundColor White }
function Write-Ok { param($msg) Write-Host "  ✓ $msg" -ForegroundColor Green }
function Write-Warn { param($msg) Write-Host "  ⚠ $msg" -ForegroundColor Yellow }
function Write-Err { param($msg) Write-Host "  ✗ $msg" -ForegroundColor Red }
function Write-Info { param($msg) Write-Host "  ℹ $msg" -ForegroundColor DarkGray }

function Confirm-Step {
    param($msg)
    if ($DryRun) {
        Write-Info "[DRY RUN] Would execute: $msg"
        return $false
    }
    Write-Host ""
    $response = Read-Host "  Press ENTER to proceed with '$msg', or type 'skip' to skip"
    return ($response -ne "skip")
}

# ─── Banner ──────────────────────────────────────────────────────────────────
Write-Host ""
Write-Host "  ╔═══════════════════════════════════════════════════════════╗" -ForegroundColor Magenta
Write-Host "  ║       BookEase24X7 — Supabase Migration Script          ║" -ForegroundColor Magenta
Write-Host "  ║                                                         ║" -ForegroundColor Magenta
Write-Host "  ║  OLD: $OLD_PROJECT_ID                     ║" -ForegroundColor Magenta
Write-Host "  ║  NEW: $NEW_PROJECT_ID                     ║" -ForegroundColor Magenta
Write-Host "  ╚═══════════════════════════════════════════════════════════╝" -ForegroundColor Magenta
Write-Host ""

if ($DryRun) {
    Write-Warn "DRY RUN MODE — no changes will be made"
    Write-Host ""
}

###############################################################################
# PHASE 1: Prerequisites
###############################################################################
Write-Phase "PHASE 1: Prerequisites Check"

# Check Supabase CLI
Write-Step "Checking Supabase CLI..."
try {
    $version = & supabase --version 2>&1
    Write-Ok "Supabase CLI found: $version"
}
catch {
    Write-Err "Supabase CLI not found!"
    Write-Info "Install it with: npm install -g supabase"
    Write-Info "Or see: https://supabase.com/docs/guides/cli"
    exit 1
}

# Check we're in the right directory
Write-Step "Checking project directory..."
if (Test-Path "$PROJECT_ROOT\supabase\config.toml") {
    Write-Ok "Found supabase/config.toml"
}
else {
    Write-Err "Cannot find supabase/config.toml in $PROJECT_ROOT"
    Write-Info "Make sure to run this script from the 'scripts' directory"
    exit 1
}

if (Test-Path "$PROJECT_ROOT\.env") {
    Write-Ok "Found .env file"
}
else {
    Write-Err "Cannot find .env in $PROJECT_ROOT"
    exit 1
}

# Check current config
Write-Step "Current config.toml project_id:"
$currentProjectId = (Get-Content "$PROJECT_ROOT\supabase\config.toml" | Select-String 'project_id').Line
Write-Info $currentProjectId

###############################################################################
# PHASE 2: Link to New Project
###############################################################################
Write-Phase "PHASE 2: Link Supabase CLI to New Project"

Write-Step "This will run: supabase link --project-ref $NEW_PROJECT_ID"
Write-Warn "You will be prompted for the DATABASE PASSWORD of the new project."
Write-Info "Find it in Supabase Dashboard → Settings → Database → Connection string"

if (Confirm-Step "Link to new project") {
    Set-Location $PROJECT_ROOT
    Write-Step "Linking..."
    & supabase link --project-ref $NEW_PROJECT_ID
    if ($LASTEXITCODE -eq 0) {
        Write-Ok "Successfully linked to $NEW_PROJECT_ID"
    }
    else {
        Write-Err "Failed to link! Exit code: $LASTEXITCODE"
        Write-Info "Common fixes: check project ID, database password, or run 'supabase login' first"
        exit 1
    }
}

###############################################################################
# PHASE 3: Push Schema (36 Migrations)
###############################################################################
if (-not $SkipSchema) {
    Write-Phase "PHASE 3: Push Database Schema (36 migrations)"

    # List migrations
    $migrations = Get-ChildItem "$PROJECT_ROOT\supabase\migrations\*.sql" | Sort-Object Name
    Write-Step "Found $($migrations.Count) migration files:"
    foreach ($m in $migrations) {
        Write-Info "  $($m.Name)"
    }

    Write-Host ""
    Write-Step "This will run: supabase db push"
    Write-Warn "This applies ALL migrations to the new project's database in order."
    Write-Warn "The new project database must be EMPTY for this to work."

    if (Confirm-Step "Push schema to new project") {
        Set-Location $PROJECT_ROOT
        Write-Step "Pushing schema..."
        & supabase db push
        if ($LASTEXITCODE -eq 0) {
            Write-Ok "Schema pushed successfully! $($migrations.Count) migrations applied."
        }
        else {
            Write-Err "Schema push failed! Exit code: $LASTEXITCODE"
            Write-Info "Check the error above. You may need to:"
            Write-Info "  1. Reset the new project database (Dashboard → Settings → Database → Reset)"
            Write-Info "  2. Fix any conflicting SQL in the migration files"
            Write-Info "  3. Run 'supabase db push' again"
            exit 1
        }
    }
}
else {
    Write-Phase "PHASE 3: Push Database Schema — SKIPPED (--SkipSchema flag)"
}

###############################################################################
# PHASE 4: Deploy Edge Functions (55 functions)
###############################################################################
if (-not $SkipFunctions) {
    Write-Phase "PHASE 4: Deploy Edge Functions (55 functions)"

    $functionsDir = "$PROJECT_ROOT\supabase\functions"
    $functions = Get-ChildItem -Directory $functionsDir | Sort-Object Name
    Write-Step "Found $($functions.Count) edge functions:"

    $col = 0
    $line = "  "
    foreach ($fn in $functions) {
        $line += "$($fn.Name)".PadRight(36)
        $col++
        if ($col -eq 3) {
            Write-Info $line
            $line = "  "
            $col = 0
        }
    }
    if ($col -gt 0) { Write-Info $line }

    Write-Host ""
    Write-Step "This will run: supabase functions deploy (for each function)"

    if (Confirm-Step "Deploy all edge functions") {
        Set-Location $PROJECT_ROOT
        $successCount = 0
        $failCount = 0
        $failedFunctions = @()

        foreach ($fn in $functions) {
            Write-Step "Deploying $($fn.Name)..."
            & supabase functions deploy $fn.Name --no-verify-jwt 2>&1 | Out-Null
            if ($LASTEXITCODE -eq 0) {
                Write-Ok "$($fn.Name) deployed"
                $successCount++
            }
            else {
                Write-Err "$($fn.Name) FAILED"
                $failCount++
                $failedFunctions += $fn.Name
            }
        }

        Write-Host ""
        Write-Ok "$successCount functions deployed successfully"
        if ($failCount -gt 0) {
            Write-Err "$failCount functions failed:"
            foreach ($failed in $failedFunctions) {
                Write-Err "  - $failed"
            }
            Write-Warn "You can retry failed functions individually:"
            Write-Info "  supabase functions deploy <function-name>"
        }
    }
}
else {
    Write-Phase "PHASE 4: Deploy Edge Functions — SKIPPED (--SkipFunctions flag)"
}

###############################################################################
# PHASE 5: Set Edge Function Secrets
###############################################################################
if (-not $SkipSecrets) {
    Write-Phase "PHASE 5: Set Edge Function Secrets"

    Write-Step "The following secrets need to be set on the new project:"
    Write-Host ""
    Write-Host "  ┌────┬──────────────────────────┬──────────────────────────────────────┐" -ForegroundColor DarkGray
    Write-Host "  │ #  │ Secret Name              │ Status                               │" -ForegroundColor DarkGray
    Write-Host "  ├────┼──────────────────────────┼──────────────────────────────────────┤" -ForegroundColor DarkGray
    Write-Host "  │  1 │ STRIPE_SECRET_KEY        │ Provided ✓                           │" -ForegroundColor Green
    Write-Host "  │  2 │ STRIPE_WEBHOOK_SECRET    │ ⚠ Not provided - set manually later  │" -ForegroundColor Yellow
    Write-Host "  │  3 │ BREVO_API_KEY            │ Provided ✓                           │" -ForegroundColor Green
    Write-Host "  │  4 │ BREVO_SENDER_EMAIL       │ ⚠ Not provided - set manually later  │" -ForegroundColor Yellow
    Write-Host "  │  5 │ BREVO_SENDER_NAME        │ Using default: BookEase              │" -ForegroundColor Green
    Write-Host "  │  6 │ DAILY_API_KEY            │ Provided ✓                           │" -ForegroundColor Green
    Write-Host "  │  7 │ OPENAI_API_KEY           │ ⚠ Not provided - set manually later  │" -ForegroundColor Yellow
    Write-Host "  │  8 │ TWILIO_ACCOUNT_SID       │ ⚠ Not provided - set manually later  │" -ForegroundColor Yellow
    Write-Host "  │  9 │ TWILIO_AUTH_TOKEN        │ ⚠ Not provided - set manually later  │" -ForegroundColor Yellow
    Write-Host "  │ 10 │ TWILIO_PHONE_NUMBER      │ ⚠ Not provided - set manually later  │" -ForegroundColor Yellow
    Write-Host "  │ 11 │ VAPID_PUBLIC_KEY         │ ⚠ Not provided - set manually later  │" -ForegroundColor Yellow
    Write-Host "  │ 12 │ VAPID_PRIVATE_KEY        │ ⚠ Not provided - set manually later  │" -ForegroundColor Yellow
    Write-Host "  │ 13 │ RESEND_API_KEY           │ ⚠ Not provided - set manually later  │" -ForegroundColor Yellow
    Write-Host "  └────┴──────────────────────────┴──────────────────────────────────────┘" -ForegroundColor DarkGray

    Write-Host ""
    Write-Step "Setting the provided secrets now..."

    if (Confirm-Step "Set the 4 provided secrets (Stripe, Brevo, Daily.co)") {
        Set-Location $PROJECT_ROOT

        Write-Step "Setting STRIPE_SECRET_KEY..."
        & supabase secrets set STRIPE_SECRET_KEY="YOUR_STRIPE_SECRET_KEY_HERE"
        if ($LASTEXITCODE -eq 0) { Write-Ok "STRIPE_SECRET_KEY set" } else { Write-Err "Failed to set STRIPE_SECRET_KEY" }

        Write-Step "Setting BREVO_API_KEY..."
        & supabase secrets set BREVO_API_KEY="YOUR_BREVO_API_KEY_HERE"
        if ($LASTEXITCODE -eq 0) { Write-Ok "BREVO_API_KEY set" } else { Write-Err "Failed to set BREVO_API_KEY" }

        Write-Step "Setting BREVO_SENDER_NAME..."
        & supabase secrets set BREVO_SENDER_NAME="BookEase"
        if ($LASTEXITCODE -eq 0) { Write-Ok "BREVO_SENDER_NAME set" } else { Write-Err "Failed to set BREVO_SENDER_NAME" }

        Write-Step "Setting DAILY_API_KEY..."
        & supabase secrets set DAILY_API_KEY="YOUR_DAILY_API_KEY_HERE"
        if ($LASTEXITCODE -eq 0) { Write-Ok "DAILY_API_KEY set" } else { Write-Err "Failed to set DAILY_API_KEY" }

        Write-Ok "All provided secrets have been set!"
    }

    Write-Host ""
    Write-Warn "REMAINING SECRETS — set these manually when you have the values:"
    Write-Host ""
    Write-Info "supabase secrets set STRIPE_WEBHOOK_SECRET=`"whsec_your_value`""
    Write-Info "supabase secrets set BREVO_SENDER_EMAIL=`"noreply@yourdomain.com`""
    Write-Info "supabase secrets set OPENAI_API_KEY=`"sk-your_value`""
    Write-Info "supabase secrets set TWILIO_ACCOUNT_SID=`"AC_your_value`""
    Write-Info "supabase secrets set TWILIO_AUTH_TOKEN=`"your_value`""
    Write-Info "supabase secrets set TWILIO_PHONE_NUMBER=`"+1your_number`""
    Write-Info "supabase secrets set VAPID_PUBLIC_KEY=`"your_value`""
    Write-Info "supabase secrets set VAPID_PRIVATE_KEY=`"your_value`""
    Write-Info "supabase secrets set RESEND_API_KEY=`"re_your_value`""

}
else {
    Write-Phase "PHASE 5: Set Secrets — SKIPPED (--SkipSecrets flag)"
}

###############################################################################
# PHASE 6: Verification & Summary
###############################################################################
Write-Phase "PHASE 6: Verification & Summary"

Write-Step "Running schema lint..."
if (-not $DryRun) {
    Set-Location $PROJECT_ROOT
    & supabase db lint 2>&1 | ForEach-Object { Write-Info $_ }
}

Write-Host ""
Write-Host "  ╔═══════════════════════════════════════════════════════════╗" -ForegroundColor Green
Write-Host "  ║              Migration Script Complete!                  ║" -ForegroundColor Green
Write-Host "  ╚═══════════════════════════════════════════════════════════╝" -ForegroundColor Green
Write-Host ""

Write-Step "NEXT STEPS:"
Write-Host ""
Write-Info "1. UPDATE .env file — change the 3 Supabase env vars (script will prompt)"
Write-Info "2. UPDATE supabase/config.toml — change project_id"
Write-Info "3. RESTART dev server: npm run dev"
Write-Info "4. TEST in browser — check Network tab for new project URL"
Write-Info "5. SET remaining secrets (Twilio, OpenAI, VAPID, etc.)"
Write-Info "6. UPDATE Stripe webhook URL in Stripe Dashboard:"
Write-Info "   → $NEW_SUPABASE_URL/functions/v1/stripe-webhook"
Write-Info "7. UPDATE Brevo webhook URL in Brevo Dashboard:"
Write-Info "   → $NEW_SUPABASE_URL/functions/v1/brevo-webhook"
Write-Info "8. RE-CONFIGURE auth providers in new Supabase Dashboard"
Write-Info "9. RE-CREATE storage buckets if needed"
Write-Host ""

# Return to original directory
Set-Location $PROJECT_ROOT
