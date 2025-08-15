import { ProductCardProps } from "@/app/components/productCard";
import { NextResponse, NextRequest } from "next/server";
import { api } from "@/utils/api";


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
        description:"The Samsung Galaxy S22 Ultra is Samsung’s flagship smartphone from the S22 series, blending the power of the Galaxy S line with the productivity features of the Note series. It features a 6.8-inch Dynamic AMOLED 2X display with QHD+ resolution and a 120Hz adaptive refresh rate for ultra-smooth visuals. Powered by the Snapdragon 8 Gen 1 (or Exynos 2200 in some regions) and up to 12GB of RAM, it delivers top-tier performance."
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

// export async function GET() {
//     const res = NextResponse.json(products);
//     return res;
// }

export async function GET(req: NextRequest) {
    const res = await api.get("/inventory/api/v1/product")
    return NextResponse.json({...res})
}