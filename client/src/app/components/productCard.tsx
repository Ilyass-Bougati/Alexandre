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


export type ProductCardProps = {
    id?: string,
    name: string,
    price: string,
    imageUrl: string
}

export default function ProductCard(props: ProductCardProps) {
    return (<>
        <Drawer>
            <DrawerTrigger>
                <div className={`jetbrain ${styles.cardDiv}`}>
                    <div>
                        <Image
                            className={`p-5 center ${styles.image}`}
                            width={300}
                            height={300}
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
            <DrawerContent>
                <DrawerDescription>
                    <div className={`flex center ${styles.drawerContainer}`}>
                        <Image
                            className={`jetbrain p-5 center ${styles.image}`}
                            width={400}
                            height={400}
                            src={props.imageUrl}
                            alt=""
                        />
                        <div>
                            <DrawerHeader>
                                <DrawerTitle className={`${styles.drawerTitle}`}>{props.name}</DrawerTitle>
                            </DrawerHeader>
                            <p className={`jetbrain ${styles.drawerItemDescription}`}>Lorem ipsum dolor sit amet consectetur adipisicing elit. Eaque vel laudantium architecto aliquam adipisci officiis eius sequi hic modi, alias facilis, placeat earum fuga consequuntur delectus voluptates tempore. Architecto, nulla?</p>
                            <h1 className={`my-10 jetbrain ${styles.price}`}>{props.price}</h1>
                        </div>
                    </div>
                </DrawerDescription>
                <DrawerFooter>
                    <div className="center">
                        <DrawerClose>
                            <Button variant="mx-5 secondary">Back</Button>
                        </DrawerClose>
                        <Button>Add to Card</Button>
                    </div>
                </DrawerFooter>
            </DrawerContent>
        </Drawer>
    </>)
}