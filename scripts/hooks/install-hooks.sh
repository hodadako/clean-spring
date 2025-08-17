#!/usr/bin/env bash

LINTER_CURRENT_DIR=$(cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd)
GRADLEW_SCRIPT_PATH="$LINTER_CURRENT_DIR/../../splearn/gradlew"
GIT_HOOK_DIR="$LINTER_CURRENT_DIR/../../.git/hooks"
DETEKT_TARGET_FILE="$GIT_HOOK_DIR/pre-push"
DETEKT_HOOK_SCRIPT="$LINTER_CURRENT_DIR/.detekt_hook_script.orig"

if [[ ! -d "$GIT_HOOK_DIR" ]]
then
mkdir -p "$DETEKT_SCRIPT_DIR"
fi

if [[ -f "$DETEKT_TARGET_FILE" ]]
then
echo "*************************************************"
echo " Detekt integration failed "
echo "*************************************************"
echo "Reason: GIT pre-commit hook is already installed!"
exit 1
fi

eval "$GRADLEW_SCRIPT_PATH addKtlintCheckGitPreCommitHook"

cat "$DETEKT_HOOK_SCRIPT" > "$DETEKT_TARGET_FILE"
chmod +x "$DETEKT_TARGET_FILE"

echo "************************************************"
echo " Linter installed "
echo "************************************************"
echo "Install path: $(ls "$GIT_HOOK_DIR")"
