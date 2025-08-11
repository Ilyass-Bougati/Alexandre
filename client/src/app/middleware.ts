import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

export function middleware(req: NextRequest) {
  const token = req.cookies.get("access_token")?.value;

  if (!token) {
    return NextResponse.redirect(new URL("/login", req.url));
  }

  // Only existence check here
  return NextResponse.next();
}

// Optional: restrict middleware only to certain routes
// TODO : change these later
export const config = {
  matcher: ["/cart/:path*", "/profile/:path*"],
};