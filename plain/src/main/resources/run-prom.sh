#!/usr/bin/env bash

function run() {
  docker run -d --network host -v $(pwd)/prometheus.yml:/etc/prometheus/prometheus.yml:rw prom/prometheus:latest
}

run
