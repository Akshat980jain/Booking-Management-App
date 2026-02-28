# ============================================================================
# Supabase Data Migration Script
# ============================================================================
# This script extracts data (user accounts and public tables) from the OLD 
# Supabase project and pushes it to the NEW Supabase project.
# ============================================================================

$OldProjectId = "eqbtrurgizszxdwnhhlk"
$NewProjectId = "qmznlttogejdbcnrxggt"
$Timestamp = Get-Date -Format "yyyyMMddHHmmss"
$MigrationFile = "supabase/migrations/${Timestamp}_import_data.sql"

Write-Host "===============================================" -ForegroundColor Cyan
Write-Host " Supabase Data Migration Tool" -ForegroundColor Cyan
Write-Host "===============================================" -ForegroundColor Cyan
Write-Host "This script will migrate your user accounts (auth) and application data (public)"
Write-Host "from the old project to the new one.`n"

# Step 1: Link to OLD project and dump data
Write-Host "STEP 1: Connecting to OLD project ($OldProjectId)..." -ForegroundColor Yellow
Write-Host "Please enter the database password for the OLD project when prompted." -ForegroundColor Magenta
npx -y supabase link --project-ref $OldProjectId
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Failed to link to old project." -ForegroundColor Red
    exit 1
}

Write-Host "`nExtracting data from auth and public schemas (this may take a minute)..." -ForegroundColor Yellow
npx -y supabase db dump --data-only --schema public, auth > $MigrationFile
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Failed to dump data from old project." -ForegroundColor Red
    exit 1
}
Write-Host "✓ Data successfully extracted to $MigrationFile" -ForegroundColor Green

Write-Host "Disabling database triggers in migration file for safe data restore..." -ForegroundColor Yellow
$DumpContent = Get-Content -Path $MigrationFile -Raw -Encoding UTF8
"SET session_replication_role = replica;`n" + $DumpContent | Set-Content -Path $MigrationFile -Encoding UTF8
# Step 2: Link to NEW project and push data
Write-Host "`nSTEP 2: Connecting to NEW project ($NewProjectId)..." -ForegroundColor Yellow
Write-Host "Please enter the database password for the NEW project when prompted." -ForegroundColor Magenta
npx -y supabase link --project-ref $NewProjectId
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Failed to link to new project." -ForegroundColor Red
    exit 1
}

Write-Host "`nPushing extracted data to new project..." -ForegroundColor Yellow
npx -y supabase db push
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Failed to push data to new project." -ForegroundColor Red
    exit 1
}
Write-Host "✓ Data successfully restored to new project!" -ForegroundColor Green

# Step 3: Cleanup
Write-Host "`nSTEP 3: Cleaning up..." -ForegroundColor Yellow
Remove-Item -Path $MigrationFile -Force -ErrorAction SilentlyContinue
Write-Host "✓ Cleanup complete." -ForegroundColor Green

Write-Host "`n===============================================" -ForegroundColor Cyan
Write-Host " Data Migration Complete! 🎉" -ForegroundColor Green
Write-Host "===============================================" -ForegroundColor Cyan
Write-Host "Data migration has completed successfully."
