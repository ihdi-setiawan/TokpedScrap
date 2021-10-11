#!/usr/bin/env bash

#!/usr/bin/env bash

# Usage: ./run.sh -p [webdriver-path]
while getopts p: opts; do
    case ${opts} in
        p) WEBDRIVER_PATH=${OPTARG} ;;
    esac
done

if [ -z "$WEBDRIVER_PATH" ]; then
    echo "arg -p WEBDRIVER PATH is required."
    exit 1
fi

# Build and deploy
mvn clean compile exec:java -Dexec.args="$WEBDRIVER_PATH"