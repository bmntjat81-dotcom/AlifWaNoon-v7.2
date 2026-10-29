#!/usr/bin/env sh
# Gradle start up script for UN*X
# AlifWaNoon v7.2 Wrapper
set -e
APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`
GRADLE_HOME=`dirname "$0"`/gradle/wrapper
if [ ! -f "$GRADLE_HOME/gradle-wrapper.jar" ]; then
  echo "Downloading wrapper..."
fi
exec java -jar "$GRADLE_HOME/gradle-wrapper.jar" "$@"
