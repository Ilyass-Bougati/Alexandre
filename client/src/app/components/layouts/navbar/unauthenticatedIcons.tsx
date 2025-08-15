import Link from "next/link"

export default function UnauthenticatedIcons() {
    return (<>
        <div className="flex items-center space-x-6 rtl:space-x-reverse jetbrain text-lg">
            <Link href="/register" className="hover:underline">
                Sign up
            </Link>
            <Link href="/login" className="hover:underline">
                Login
            </Link>
        </div>
    </>)
}