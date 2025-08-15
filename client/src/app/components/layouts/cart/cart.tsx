import { SheetContent, SheetDescription, SheetFooter, SheetHeader, SheetTitle } from "@/components/ui/sheet"
import CartElement from "./cartElement"
import styles from "./Cart.module.css"
import { Button } from "@/components/ui/button"

export type CartElementProps = {
    id?: string,
    name: string,
    unitPrice: number,
    imageUrl: string,
    quantity: number
}

const data: Array<CartElementProps> = [
    {
        id: "1",
        name: "Iphone 16",
        unitPrice: 1600,
        imageUrl: "/mock/iphone-16.png",
        quantity: 3
    },
    {
        id: "2",
        name: "realme note 50",
        unitPrice: 1000,
        imageUrl: "/mock/realme-note-50.png",
        quantity: 2
    },
]

export default function Cart() {

    const getTotal = () => {
        let sum = 0;
        data.forEach(e => {sum += e.quantity*e.unitPrice})
        return sum;
    }

    return (<>
        <SheetContent className="w-[600px] sm:max-w-lg">
            <SheetHeader className="ml-3 mt-3">
                <SheetTitle>
                    Your Cart
                </SheetTitle>
                <SheetDescription>
                    Verify your cart elements before moving to the checkout
                </SheetDescription>
            </SheetHeader>
            {
                data.map((element: CartElementProps) => {
                    return <CartElement 
                        key={element.id}
                        name={element.name}
                        unitPrice={element.unitPrice}
                        imageUrl={element.imageUrl}
                        quantity={element.quantity}
                    />
                })
            }
            <SheetFooter className="mb-5">
                <h1 className={`flex gap-3 ml-2 jetbrain ${styles.total}`}>Total: <p className={`${styles.price}`}>{getTotal()}$</p></h1>
                <div className="center p-y-5">
                    <Button variant="secondary">Back</Button>
                    <Button className="ml-5">Checkout</Button>
                </div>
            </SheetFooter>
        </SheetContent>
    </>)
}