#!/bin/bash

REPO_OWNER="AnonimProgrammer"
REPO_NAME="CI-CD-TestRepo"
BRANCH="master"
DEPLOYMENT_NAME="test-app"
IMAGE_NAME="dockerforomar/test-app:latest"
SHA_FILE="./.last_sha"

# Get the latest commit SHA from GitHub
LATEST_SHA=$(curl -s https://api.github.com/repos/${REPO_OWNER}/${REPO_NAME}/commits/${BRANCH} | jq -r '.sha')

# Read the last known SHA
LAST_SHA=$(cat "$SHA_FILE" 2>/dev/null)

# Compare and rollout if needed
if [ "$LATEST_SHA" != "$LAST_SHA" ]; then
  echo "$(date): New commit detected. Restarting deployment..."
  kubectl set image deployment/$DEPLOYMENT_NAME $DEPLOYMENT_NAME=$IMAGE_NAME --record
  echo "$LATEST_SHA" > "$SHA_FILE"
else
  echo "$(date): No new commit. Skipping."
fi
