import { NextResponse, NextRequest } from "next/server";
import { api } from "@/utils/api";

export async function POST(req: NextRequest) {
    const token = req.cookies.get("access_token")?.value;

    // checking if a token exists
    if (token !== undefined) {
        return NextResponse.json({ error: "Already logged in" }, { status: 400 })
    }

    // sending the request
    const body = await req.json();
    await api.post(
        "/auth/api/v1/register/",
        {...body},
        {headers: { 'Content-Type': 'application/json' }}
    )

    return NextResponse.json({ status: 400 })
}