#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
mvn --batch-mode package
printf '%s\n' 'Abra http://127.0.0.1:8080 no navegador.'
exec java -jar apresentacao-backend/target/oficina-campus.jar "$@"
