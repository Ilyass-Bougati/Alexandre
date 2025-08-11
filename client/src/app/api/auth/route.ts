import { NextResponse, NextRequest } from "next/server";

/**
 * This function authenticates the user
 * @param req The request should contain `access_token` and `refresh_token`
 * @returns a response of `200` in the case of no error
 */
export async function POST(req: Request) {
  const { access_token, refresh_token } = await req.json();

  const res = NextResponse.json({ success: true });
  
  // saving the access token
  res.cookies.set("access_token", access_token, {
    httpOnly: true,
    secure: process.env.NODE_ENV === "production",
    sameSite: "strict",
    path: "/",
    maxAge: 60 * 15, // 15 min
    expires: new Date(Date.now() + 15 * 60 * 1000),
  });

  res.cookies.set("refresh_token", refresh_token, {
    httpOnly: true,
    secure: process.env.NODE_ENV === "production",
    sameSite: "strict",
    path: "/api/refresh", // Only sent when hitting refresh endpoint
    maxAge: 60 * 60 * 24 * 7, // 7 days
    expires: new Date(Date.now() + 7 * 24 * 60 * 60 * 1000),
  });

  return res;
}

/**
 * This function checks if the user is authenticated
 * @param req the request doesn't have to send any body data, since we only need the cookies inside
 * @returns status code `200` if authenticated, and `401` otherwise
 */
export async function GET(req: NextRequest) {
    const token = req.cookies.get("access_token")?.value;
    if (!token) {
        return NextResponse.json({ error: "Unauthorized" }, { status: 401 });
    } else {
        return NextResponse.json({}, { status: 200 });
    }
}