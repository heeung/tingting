import styles from "./ScheduleList.module.css"
import Schedule from "../schedule/Schedule"

interface Schedule{
    seq:number;
    concertSeq:number;
    holdDate:string;
}

interface ScheduleListProps{
    concertDetails:Schedule[];
    concertName:string
}

export default function ScheduleList({concertDetails, concertName}:ScheduleListProps){
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