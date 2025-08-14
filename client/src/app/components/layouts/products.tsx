import ProductCard from "../productCard"
import styles from "./Products.module.css"

export default function Products() {

    return (
        <div className={`jetbrain ${styles.container}`}>
            <h1 className={`pt-10 pb-5 ${styles.title}`}>Our products</h1>
            <div className={`flex ${styles.products}`}>
                <ProductCard />
                <ProductCard />
                <ProductCard />
                <ProductCard />
            </div>
        </div>
    )
}