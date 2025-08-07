#!/bin/bash

# Check if we're in a git repository
if ! git rev-parse --is-inside-work-tree > /dev/null 2>&1; then
    echo "Error: Not a git repository"
    exit 1
fi

# Check if generated directory exists
if [ ! -d "src/generated" ]; then
    echo "Error: 'generated' directory does not exist"
    exit 1
fi

# Check for any changes in the src/generated directory
changed_files=$(git ls-files --others --exclude-standard src/generated/)
modified_files=$(git diff --name-only -- src/generated/)

if [ -z "$changed_files" ] && [ -z "$modified_files" ]; then
    echo "No changes to commit in the 'generated' directory"
    exit 0
fi

echo "Detected changes in the 'generated' directory:"
if [ -n "$changed_files" ]; then
    echo "New files:"
    echo "$changed_files"
    echo ""
fi
if [ -n "$modified_files" ]; then
    echo "Modified files:"
    echo "$modified_files"
    echo ""
fi

# Add all files from the src/generated directory
git add src/generated/

# Create a commit with the message "datagen"
git commit -m "datagen"

echo "Successfully committed changes in the 'generated' directory"
