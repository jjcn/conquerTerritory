#!/bin/bash

gradle build || exit 1
gradle cloverAggregateReports || exit 1
scripts/coverage_summary.sh
ls -l /
ls -l /coverage-out/
cp -r build/reports/clover/html/* /coverage-out/ || exit 1
