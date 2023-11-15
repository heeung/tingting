import styles from './ConcertInfo.module.css'
import { useNavigate } from 'react-router-dom'

interface Concert{
    concertSeq:number;
    name:string;
    holdOpenDate:string;
    holdCloseDate:string;
    imageUrl:string;
    concertHallName:string;
    concertHallCity:string;
}

interface ConcertInfoProps{
    concert:Concert;
}

export default function ConcertInfo({concert}:ConcertInfoProps){

    const navigate = useNavigate()
    const goToOtherPage = (pageName:string) => {
        navigate(`/${pageName}`);
    }

    return(
        <div
        onClick={()=>goToOtherPage(`concert/detail/${concert.concertSeq}`)}
         className={styles.container}>

            <img 
            className={styles.img}
            src={concert.imageUrl} alt="concertImg" />

            <div
            className={styles.concertinfo}>
                <div
                 className={styles.concertName}>
                        {concert.name}
                </div>
                {/* <div
                 className={styles.concertDate}>
                    공연날짜
                </div>
                <div
                 className={styles.concertLocate}>
                    공연장소
                </div> */}

            </div>
        
        </div>
    )
}