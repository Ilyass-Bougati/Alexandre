#!/usr/bin/bash

keytool -genkeypair \
  -alias keycloak \
  -keyalg RSA \
  -keysize 2048 \
  -validity 365 \
  -keystore keys/keycloak.jks \
  -storepass password \
  -keypass password \
  -dname "CN=your-ec2-ip"

keytool -genkeypair \
  -alias myalias \
  -keyalg RSA \
  -keysize 2048 \
  -storetype PKCS12 \
  -keystore keystore.p12 \
  -validity 3650 \
  -storepass password