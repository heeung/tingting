import styles from './ConcertInfo.module.css'
import { concertImg } from '/src/assets/Images/'

export default function ConcertInfo(){

    return(
        <div
         className={styles.container}>

            <img 
            className={styles.img}
            src={concertImg} alt="concertImg" />

            <div
            className={styles.concertinfo}>
                <div
                 className={styles.concertName}>
                    공연이름
                </div>
                <div
                 className={styles.concertDate}>
                    공연날짜
                </div>
                <div
                 className={styles.concertLocate}>
                    공연장소
                </div>

            </div>
        
        </div>
    )
}