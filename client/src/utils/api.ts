import axios from "axios";

let accessToken = localStorage.getItem("access_token")
// let refreshToken = localStorage.getItem("refresh_token")

export const keycloakApi = axios.create({
  baseURL: process.env.NEXT_PUBLIC_KEYCLOAK_URL,
  headers: {
    Authorization: `Bearer ${accessToken}`
  },
  withCredentials: true
});


export const api = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_GATEWAY,
  headers: {
    Authorization: `Bearer ${accessToken}`
  },
  withCredentials: true
})