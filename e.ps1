# Get list of changed files from git
$file_arr = git status --porcelain | ForEach-Object {
    # Format is: "XY filename"
    # We extract only the filename after the status flags
    ($_ -replace '^\S+\s+', '')
}

if ($file_arr.Count -eq 0) {
    Write-Host "No modified files found."
    exit
}

foreach ($file in $file_arr) {
    
    $file_name = Split-Path $file -Leaf

    if ($file_name -eq "e.ps1") {
        continue
    }
    Write-Host "Committing: $file_name"

    git add $file
    git commit -m "Updated $file_name"
}

Write-Host "Done!"
# Get list of changed files from git
$file_arr = git status --porcelain | ForEach-Object {
    # Format is: "XY filename"
    # We extract only the filename after the status flags
    ($_ -replace '^\S+\s+', '')
}

if ($file_arr.Count -eq 0) {
    Write-Host "No modified files found."
    exit
}

foreach ($file in $file_arr) {
    $file_name = Split-Path $file -Leaf

    Write-Host "Committing: $file_name"

    git add $file
    git commit -m "Updated $file_name"
}

Write-Host "Done!"
