'use client'

import styles from "./ProductCard.module.css"
import Image from "next/image"
import {
  Drawer,
  DrawerClose,
  DrawerContent,
  DrawerDescription,
  DrawerFooter,
  DrawerHeader,
  DrawerTitle,
  DrawerTrigger,
} from "@/components/ui/drawer"
import { Button } from "@/components/ui/button"
import { useState } from "react"
import axios from "axios"
import { useRouter } from "next/navigation"
import { MouseEvent } from "react"
import { Loader2Icon } from "lucide-react"

export type ProductCardProps = {
    id?: string,
    name: string,
    price: string,
    imageUrl: string,
    description: string
}

export default function ProductCard(props: ProductCardProps) {

    const [loading, setLoading] = useState<boolean>(false)
    const router = useRouter()

    const handleAddToCardHandler = (e: MouseEvent) => {
        setLoading(true)
        axios.get("/api/auth")
            .then((res) => {
                if (res.status == 200) {
                    // add the logic here
                    console.log(e.type)
                } else {
                    router.push("/login")
                }
            })
            .catch(() => {
                router.push("/login")
            })
            .finally(() => {
                setLoading(false)
            })
    }

    return (<>
        <Drawer>
            <DrawerTrigger>
                <div className={`jetbrain ${styles.cardDiv}`}>
                    <div>
                        <Image
                            className={`p-5 center ${styles.image}`}
                            width={250}
                            height={250}
                            src={props.imageUrl}
                            alt=""
                        />
                    </div>
                    <div>
                        <h1 className={`pl-5 ${styles.productName}`}>{props.name}</h1>
                        <p className={`pr-5 pb-5 ${styles.price}`}>{props.price}</p>
                    </div>
                </div>
            </DrawerTrigger>
            <DrawerContent className="sm:max-h-lg">
                <DrawerDescription>
                    <div className={`flex center ${styles.drawerContainer}`}>
                        <Image
                            className={`jetbrain p-5 center ${styles.image}`}
                            width={400}
                            height={400}
                            src={props.imageUrl}
                            alt=""
                            priority
                        />
                        <div>
                            <DrawerHeader>
                                <DrawerTitle className={`${styles.drawerTitle}`}>{props.name}</DrawerTitle>
                            </DrawerHeader>
                            <p className={`jetbrain ${styles.drawerItemDescription}`}>{props.description}</p>
                            <h1 className={`my-10 jetbrain ${styles.price}`}>{props.price}</h1>
                        </div>
                    </div>
                </DrawerDescription>
                <DrawerFooter>
                    <div className="center">
                        <DrawerClose>
                            <Button variant="secondary">Back</Button>
                        </DrawerClose>
                            {loading ? 
                                <Button id={props.id} variant="ghost" onClick={handleAddToCardHandler}>
                                    <Loader2Icon className="animate-spin"/> 
                                    Adding...
                                </Button>
                            :
                                <Button id={props.id} onClick={handleAddToCardHandler}>
                                    Add to Card
                                </Button>
                            }
                            
                    </div>
                </DrawerFooter>
            </DrawerContent>
        </Drawer>
    </>)
}