'use client'

import ProductCard from "../productCard"
import styles from "./Products.module.css"
import { ProductCardProps } from "../productCard"
import { useEffect, useState } from "react"
import { Skeleton } from "@/components/ui/skeleton"
import axios from "axios"


export default function Products() {
    const [loading, setLoading] = useState<boolean>(true)
    const [products, setProducts] = useState<Array<ProductCardProps>>([])

    useEffect(() => {
        axios.get("/api/products")
        .then(res => {
            setProducts(res.data)
            setLoading(false)
        })
    }, [])

    return (
        <div className={`jetbrain ${styles.container}`}>
            <h1 className={`pt-10 pb-5 ${styles.title}`}>Our products</h1>
            <div className={`flex ${styles.products}`}>
                {   loading ? 
                    [1,2,3,4].map((key) => {
                        return <div key={key} className="flex flex-col space-y-3">
                            <Skeleton className="h-[125px] w-[250px] rounded-xl" />
                            <div className="space-y-2">
                                <Skeleton className="h-4 w-[250px]" />
                                <Skeleton className="h-4 w-[200px]" />
                            </div>
                        </div>
                    })
                    :
                    products.map((productProps: ProductCardProps) => {
                        return <ProductCard
                                key={productProps.id}
                                name={productProps.name}
                                price={productProps.price}
                                imageUrl={productProps.imageUrl}
                                description={productProps.description}
                            />
                    })
                }
            </div>
        </div>
    )
}