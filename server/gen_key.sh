#!/usr/bin/bash

keytool -genkeypair \
  -alias keycloak \
  -keyalg RSA \
  -keysize 2048 \
  -validity 365 \
  -keystore keycloak.jks \
  -storepass password \
  -keypass password \
  -dname "CN=your-ec2-ip"
