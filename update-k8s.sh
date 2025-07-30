#!/bin/bash

REPO_OWNER="AnonimProgrammer"
REPO_NAME="CI-CD-TestRepo"
BRANCH="master"
DEPLOYMENT_NAME="test-app"
SHA_FILE="/Users/omarismailov/Desktop/MyDocs/java_education/java_advanced/ultimate-spring-boot/.last_sha"

# Get latest commit SHA from GitHub
LATEST_SHA=$(curl -s https://api.github.com/repos/${REPO_OWNER}/${REPO_NAME}/commits/${BRANCH} | jq -r '.sha')

# Load last SHA (if any)
LAST_SHA=$(cat "$SHA_FILE" 2>/dev/null)

if [ "$LATEST_SHA" != "$LAST_SHA" ]; then
  echo "$(date): New commit detected. Restarting deployment..."
  kubectl rollout restart deployment "$DEPLOYMENT_NAME"
  echo "$LATEST_SHA" > "$SHA_FILE"
else
  echo "$(date): No new commit."
fi
