import axios from "axios";
import https from "https";

const agent = new https.Agent({
  rejectUnauthorized: false, // ⚠️ disables SSL verification
});


export const keycloakApi = axios.create({
  baseURL: process.env.NEXT_PUBLIC_KEYCLOAK_URL,
  withCredentials: true,
  httpsAgent: agent
});


export const api = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_GATEWAY,
  withCredentials: true,
  httpsAgent: agent
})