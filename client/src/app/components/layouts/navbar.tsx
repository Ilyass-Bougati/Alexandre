import Link from "next/link";

export default function Navbar() {
    return (<>
        <nav className="pale-cashmere navbar">
            <div className="flex flex-wrap justify-between items-center mx-auto max-w-screen-xl p-4">
                <Link href="/" className="flex items-center space-x-3 rtl:space-x-reverse logo-text">Alexandre</Link>
                <div className="flex items-center space-x-6 rtl:space-x-reverse jetbrain">
                    <Link href="/register" className="text-sm hover:underline">Register</Link>
                    <Link href="/login" className="text-sm hover:underline">Login</Link>
                </div>
            </div>
        </nav>
    </>)
}