import styles from "./Schedule.module.css"
import 'flowbite';
import { useNavigate } from 'react-router-dom'


export default function Schedule({schedule,concertName}) {
    const navigate = useNavigate()
    const goToOtherPage = (pageName:string) => {
        navigate(`/${pageName}`,{state:{schedule,concertName}});
    }

    return (
        <div
            className={styles.container}>
            <div>{schedule.holdDate}</div>
            <button 
            onClick={()=>goToOtherPage(`concert/reservation`)}
            className={styles.button}>
                예약
            </button>
        </div>
    )

}