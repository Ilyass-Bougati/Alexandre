import { CartElementProps } from "./cart";
import styles from "./CartElement.module.css"
import Image from "next/image";


export default function CartElement(props: CartElementProps) {
    return (<div className={`jetbrain flex ${styles.container}`}>
      <span>
        <Image 
          width={75}
          height={75}
          src={props.imageUrl}
          alt=""
          className="pl-5"
        />
      </span>
      <div className="pl-5">
        <h1 className={`${styles.cartItemName}`}>{props.name}</h1>
      <div className="flex gap-2">
        <p className={`${styles.cartItemQuantity}`}>{props.quantity}x</p>
        <p className={`${styles.cartItemPrice}`}>{props.unitPrice}$</p>
        </div>
      </div>
    </div>)
}