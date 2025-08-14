import styles from "./Introduction.module.css"
import Image from "next/image"

export default function Introduction() {
    return (<>
        <div className={`flex center items-center pt-10 text-white ${styles.introduction}`}>
            <div className="pr-20">
                <h1 className={"font-serif italic text-5xl italianno " + styles.alexandreLogo}>Alexandre</h1>
            </div>
            <div className={"border-l pl-20 " + styles.writing}>
                <pre className={"jetbrain text " + styles.slogan}>{"The leading electronics \nstore in Morocco"}</pre>
                <a href="#" className={"text-orange-500 jetbrain flex " + styles.link}>Browse our catalogue 
                    <Image 
                        height={30}
                        width={30}
                        src={"/icons/right-arrow.svg"}
                        className={`ml-2 ${styles.arrow}`}
                        alt=""
                    />
                </a>
            </div>
        </div>
    </>)
}