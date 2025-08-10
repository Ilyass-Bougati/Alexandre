import Link from "next/link";
import Image from "next/image";
import { isAuthenticated } from "@/utils/jwtUtils";

export default function Navbar() {
    return (<>
        <nav className="pale-cashmere navbar">
            <div className="flex flex-wrap justify-between items-center mx-auto max-w-screen-xl p-4">
                <Link href="/" className="flex items-center space-x-3 rtl:space-x-reverse logo-text">Alexandre</Link>
                { isAuthenticated() ?
                <div className="flex items-center space-x-6 rtl:space-x-reverse jetbrain text-lg">
                    <Link href="/account" className="hover:underline">
                        <span>
                            <Image 
                                src={"/icons/account.png"} 
                                alt="" 
                                width={40}
                                height={40}
                                priority={true}
                            />
                        </span>
                    </Link>
                    <Link href="/cart" className="hover:underline">
                        <span>
                            <Image 
                                src={"/icons/cart.png"} 
                                alt="" 
                                width={40}
                                height={40}
                                priority={true}
                            />
                        </span>
                    </Link>
                </div>
                :
                <div className="flex items-center space-x-6 rtl:space-x-reverse jetbrain text-lg">
                    <Link href="/register" className="hover:underline">
                        Sign up
                    </Link>
                    <Link href="/login" className="hover:underline">
                        Login
                    </Link>
                </div>
                }
            </div>
        </nav>
    </>)
}