import styles from "./Schedule.module.css"

export default function Schedule({schedule}) {
    return (
        <div
            className={styles.container}>
            <div>{schedule.holdDate}</div>
            <div></div>
            <button></button>
        </div>
    )

}