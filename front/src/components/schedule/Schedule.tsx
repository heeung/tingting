import styles from "./Schedule.module.css";
import { useNavigate } from 'react-router-dom';
import { format } from 'date-fns';
import { useRecoilValue } from 'recoil';
import { userSeqState } from "../../recoil/UserAtom.tsx";


interface Schedule{
    seq:number;
    concertSeq:number;
    holdDate:string;
}

interface ScheduleProps{
    schedule:Schedule;
    concertName:string
}

export default function Schedule({ schedule, concertName }:ScheduleProps) {
    
    const userSeq = useRecoilValue(userSeqState);
    const navigate = useNavigate();

    const goToOtherPage = (pageName:string) => {
        if(userSeq==null){
            alert("예약을 위해서는 로그인이 필요합니다")
            return
        }
        navigate(`/${pageName}`, { state: { schedule, concertName } });
    };

    const dateObject = new Date(schedule.holdDate);

    // 날짜와 시간을 원하는 형식으로 포맷
    const formattedDate = format(dateObject, 'yyyy-MM-dd');
    const formattedTime = format(dateObject, 'HH:mm:ss');

    return (
        <div className={styles.container}>
            <div className={styles.date}>{formattedDate}</div>
            <div className={styles.time}>{formattedTime}</div>
            <button
                onClick={() => goToOtherPage(`concert/reservation`)}
                className={styles.button}
            >
                예약
            </button>
        </div>
    );
}
