import styles from "./ScheduleList.module.css"
import Schedule from "../schedule/Schedule"

export default function ScheduleList({concertDetails}){
    return(
        <div
            className={styles.container}>
                {concertDetails?.map((schedule)=>{
                return <div 
                className={styles.concertList}
                key={schedule.seq}
                >
                    <Schedule schedule={schedule}/>
                </div>
            })}
        </div>
    )

}