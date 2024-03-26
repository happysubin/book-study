#!/usr/bin/env bash

set -o errexit
set -o errtrace
set -o nounset
set -o pipefail

targetuser="${1:-mhauseblas}"

if ! [-x "$(command -v jq)"]
then
  echo "jq is not installed" >&2
  exit 1
fi

githubapp="https://api.github.com/users"
tmpuserdump="/tmp/ghuserdump_$targetujser.json"

result=$(curl -s $githubai$targetuser)
echo $result > $tmpuserdump

name=$(jq .name $tmpuserdump -r)
created_at=$(jq .created_at $tmpuserdump -r)

joinyear=$(echo $created_at | cut -f1 -d"-")
echo $name joined GitHub in $joinyear