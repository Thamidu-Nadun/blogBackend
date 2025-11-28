# push.ps1
# Author: Thamidu Nadun
# Date: 2025-11-29
# Purpose: Automatically stage and commit files with backdated commits

# Set base commit date
$baseDate = "2025-11-28"

# Navigate to project directory
Set-Location "D:\Other\programming\Apps\Web\BlogAPI\blog"

Write-Host "Starting staged/untracked file commits for date: $baseDate" -ForegroundColor Green

# Get modified, staged, and untracked files
$filesToCommit = @()
$modified = git diff --name-only
$untracked = git ls-files --others --exclude-standard

if ($modified) { $filesToCommit += $modified }
if ($untracked) { $filesToCommit += $untracked }

if ($filesToCommit.Count -eq 0) {
    Write-Host "No files to commit." -ForegroundColor Yellow
    exit
}

Write-Host "Files to commit: $($filesToCommit.Count)" -ForegroundColor Cyan

# Starting time for commits
$hour = 9
$minute = 0

function Make-Commit {
    param(
        [string]$FilePath,
        [string]$CommitMessage,
        [datetime]$CommitDateTime
    )

    Write-Host "Processing: $FilePath" -ForegroundColor Yellow

    # Stage file (without extra quotes)
    git add $FilePath

    if ($LASTEXITCODE -eq 0) {
        Write-Host "✓ Staged: $FilePath" -ForegroundColor Green

        # Set commit date environment variables
        $env:GIT_COMMITTER_DATE = $CommitDateTime.ToString("yyyy-MM-ddTHH:mm:ss")
        $env:GIT_AUTHOR_DATE = $CommitDateTime.ToString("yyyy-MM-ddTHH:mm:ss")

        git commit -m $CommitMessage --date=$env:GIT_COMMITTER_DATE

        if ($LASTEXITCODE -eq 0) {
            Write-Host "✓ Committed: $FilePath" -ForegroundColor Green
            Write-Host "  Message: $CommitMessage" -ForegroundColor Cyan
        } else {
            Write-Host "✗ Failed to commit: $FilePath" -ForegroundColor Red
        }

        # Cleanup environment variables
        $env:GIT_COMMITTER_DATE = $null
        $env:GIT_AUTHOR_DATE = $null
    } else {
        Write-Host "✗ Failed to stage: $FilePath" -ForegroundColor Red
    }

    Write-Host "---" -ForegroundColor Gray
}


# Commit files one by one
foreach ($file in $filesToCommit) {
    # Increment minute for each commit
    $commitTime = Get-Date "$baseDate ${hour}:${minute}:00"
    $commitMessage = "feat: update $([System.IO.Path]::GetFileName($file))"
    Make-Commit -FilePath $file -CommitMessage $commitMessage -CommitDateTime $commitTime

    # Add 15 minutes for next commit
    $minute += 15
    if ($minute -ge 60) {
        $hour += [math]::Floor($minute / 60)
        $minute = $minute % 60
    }

    Start-Sleep -Seconds 1  # Small delay to avoid race issues
}

Write-Host ""
Write-Host "All commits completed!" -ForegroundColor Green
Write-Host "Use 'git log --oneline' to view recent commits" -ForegroundColor Cyan
Write-Host "Use 'git push origin main' to push to remote repository" -ForegroundColor Cyan

# Show last 15 commits
git log --oneline -n 15
