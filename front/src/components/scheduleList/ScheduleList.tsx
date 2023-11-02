import styles from "./ScheduleList.module.css"
import Schedule from "../schedule/Schedule"

export default function ScheduleList(){
    const lst = [1,2,3,4,5,6]
    return(
        <div
            className={styles.container}>
                {lst.map((item:number)=>{
                return <div 
                className={styles.concertList}
                key={item}
                >
                    <Schedule/>
                </div>
            })}
        </div>
    )

}