import { ProductCardProps } from "@/app/components/productCard";
import { NextResponse } from "next/server";


const products: Array<ProductCardProps> = [
    {
        id: "1",
        name: "Iphone 16",
        price: "1,600$",
        imageUrl: "/mock/iphone-16.png",
        description:"Lorem ipsum dolor sit amet consectetur adipisicing elit. Eaque vel laudantium architecto aliquam adipisci officiis eius sequi hic modi, alias facilis, placeat earum fuga consequuntur delectus voluptates tempore. Architecto, nulla?"
    },
    {
        id: "2",
        name: "Samsung s22 ultra",
        price: "2,600$",
        imageUrl: "/mock/samsung-s22-ultra.png",
        description:"Lorem ipsum dolor sit amet consectetur adipisicing elit. Eaque vel laudantium architecto aliquam adipisci officiis eius sequi hic modi, alias facilis, placeat earum fuga consequuntur delectus voluptates tempore. Architecto, nulla?"
    },
    {
        id: "3",
        name: "Realme note 50",
        price: "1,000$",
        imageUrl: "/mock/realme-note-50.png",
        description:"Lorem ipsum dolor sit amet consectetur adipisicing elit. Eaque vel laudantium architecto aliquam adipisci officiis eius sequi hic modi, alias facilis, placeat earum fuga consequuntur delectus voluptates tempore. Architecto, nulla?"
    },
    {
        id: "4",
        name: "Blackberry",
        price: "50$",
        imageUrl: "/mock/blackberry.png",
        description:"Lorem ipsum dolor sit amet consectetur adipisicing elit. Eaque vel laudantium architecto aliquam adipisci officiis eius sequi hic modi, alias facilis, placeat earum fuga consequuntur delectus voluptates tempore. Architecto, nulla?"
    },
]

export async function GET() {
    const res = NextResponse.json(products);
    return res;
}