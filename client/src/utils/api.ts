'use client';

import axios from "axios";

export const keycloakApi = axios.create({
  baseURL: process.env.NEXT_PUBLIC_KEYCLOAK_URL,
  withCredentials: true
});


export const api = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_GATEWAY,
  withCredentials: true
})