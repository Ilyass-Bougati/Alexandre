'use client';

import Link from "next/link";
import { useState, useEffect } from "react";
import axios from "axios";

import UnauthenticatedIcons from "./unauthenticatedIcons";
import AuthenticatedIcons from "./authenticatedIcons";
import IconsSkeleton from "./iconsSkeleton";

export default function Navbar() {

    const [authenticated, setAuthenticated] = useState<boolean>(false)
    const [loading, setLoading] = useState<boolean>(true) 

    useEffect(() => {
        axios.get("/api/auth")
            .then((res) => {
                if (res.status == 200) {
                    setAuthenticated(true)
                }
            })
            .catch(() => {})
            .finally(() => {
                setLoading(false)
            })
    }, [])

    return (<>
        <nav className="pale-cashmere navbar">
            <div className="flex flex-wrap justify-between items-center mx-auto max-w-screen-xl p-4">
                <Link href="/" className="flex items-center space-x-3 rtl:space-x-reverse "></Link>
                {loading ? <IconsSkeleton /> : authenticated ? <AuthenticatedIcons /> : <UnauthenticatedIcons />}
            </div>
        </nav>
    </>)
}