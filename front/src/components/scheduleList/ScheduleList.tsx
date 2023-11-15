import styles from "./ScheduleList.module.css"
import Schedule from "../schedule/Schedule"

export default function ScheduleList({concertDetails, concertName}){
    return(
        <div
            className={styles.container}>
                <div
                className={styles.header}>
                    <div className={styles.date}>날짜</div>
                    <div className={styles.time}>시간</div>
                </div>
                {concertDetails?.map((schedule)=>{
                return <div 
                className={styles.concertList}
                key={schedule.seq}
                >
                    <Schedule schedule={schedule} concertName={concertName}/>
                </div>
            })}
        </div>
    )

}