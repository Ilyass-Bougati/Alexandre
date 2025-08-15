'use client';

import Navbar from "./components/layouts/navbar/navbar";
import Introduction from "./components/introduction";
import Products from "./components/layouts/products";

export default function Home() {
  return (
    <>
      <Navbar />
      <Introduction />
      <Products />
    </>
  );
}
