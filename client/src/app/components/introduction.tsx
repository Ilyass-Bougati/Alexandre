import styles from "./Introduction.module.css"

export default function Introduction() {
    return (<>
        <div className={`flex items-center pt-10 text-white ${styles.introduction} ${styles.center}`}>
            <div className="pr-20">
                <h1 className={"font-serif italic text-5xl italianno " + styles.alexandreLogo}>Alexandre</h1>
            </div>
            <div className={"border-l pl-20 " + styles.writing}>
                <pre className={"jetbrain text " + styles.slogan}>{"The leading electronics \nstore in Morocco"}</pre>
                <a href="#" className={"text-orange-500 jetbrain hover:underline " + styles.link}>Browse our catalogue →</a>
            </div>
        </div>
    </>)
}