#!/bin/bash
sudo mkdir coverage
sudo docker run --rm -v `pwd`/coverage:/coverage-out  citest scripts/test.sh
