'use client';

export function isAuthenticated(): boolean {
    return localStorage.getItem("access_token") !== null
}

export function authenticate(access_token: string, refresh_token: string) {
    localStorage.setItem('access_token', access_token);
    localStorage.setItem('refresh_token', refresh_token);
}
