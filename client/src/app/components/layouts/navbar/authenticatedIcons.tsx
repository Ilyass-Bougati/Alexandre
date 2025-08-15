import Link from "next/link"
import Image from "next/image"
import styles from "./AuthenticatedIcons.module.css"
import {
  Sheet,
  SheetTrigger,
} from "@/components/ui/sheet"
import Card from "../cart/cart"

export default function AuthenticatedIcons() {
    return (<>
        <div className="flex items-center space-x-6 rtl:space-x-reverse jetbrain text-lg">
            <Link href="/account" className={`${styles.profileIcon}`}>
                <span>
                    <Image 
                        src={"/icons/account.png"} 
                        alt="" 
                        width={40}
                        height={40}
                        priority={true}
                        className={`${styles.profileImage}`}
                    />
                </span>
            </Link>
            <Sheet>
                <SheetTrigger>
                    <div className={`${styles.cartIcon}`}>
                        <Image 
                            src={"/icons/cart.png"} 
                            alt="" 
                            width={40}
                            height={40}
                            priority={true}
                        />
                    </div>
                </SheetTrigger>
                <Card />
            </Sheet>
        </div>
    </>)
}