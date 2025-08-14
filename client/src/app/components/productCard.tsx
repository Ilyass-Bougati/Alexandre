import styles from "./ProductCard.module.css"
import Image from "next/image"

export default function ProductCard() {
    return (<>
        <div className={`jetbrain ${styles.cardDiv}`}>
            <div>
                <Image
                    className={`${styles.image}`}
                    width={300}
                    height={300}
                    src={"/mock/iphone-16.png"}
                    alt=""
                />
            </div>
            <div>
                <h1 className={`pl-5 ${styles.productName}`}>Iphone 16</h1>
                <p className={`pr-5 pb-5 ${styles.price}`}>1,600$</p>
            </div>
        </div>
    </>)
}