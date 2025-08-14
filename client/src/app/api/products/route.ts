import { ProductCardProps } from "@/app/components/productCard";
import { NextResponse, NextRequest } from "next/server";


const products: Array<ProductCardProps> = [
    {
        id: "1",
        name: "Iphone 16",
        price: "1,600$",
        imageUrl: "/mock/iphone-16.png"
    },
    {
        id: "2",
        name: "Samsung s22 ultra",
        price: "2,600$",
        imageUrl: "/mock/samsung-s22-ultra.png"
    },
    {
        id: "3",
        name: "Realme note 50",
        price: "1,000$",
        imageUrl: "/mock/realme-note-50.png"
    },
    {
        id: "4",
        name: "Blackberry",
        price: "50$",
        imageUrl: "/mock/blackberry.png"
    },
]

export async function GET() {
    const res = NextResponse.json(products);
    return res;
}