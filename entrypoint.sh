#!/bin/bash

exec java ${JAVA_OPTS} \
 "-javaagent:/newrelic/newrelic.jar" \
 "-Dnewrelic.config.file=/newrelic/newrelic.yml" \
 "-Dnewrelic.config.log_file_name=STDOUT" \
 "-Dspring.profiles.active=docker" \
 "-Dnewrelic.config.app_name=product-price-converter'" \
 "-Dnewrelic.config.distributed_tracing.enabled=true" \
 "-Dnewrelic.config.license_key='8787ec2535a69fd6c36178f42e02a5a47dbbNRAL'" \
 -jar "product-price-converter.jar"