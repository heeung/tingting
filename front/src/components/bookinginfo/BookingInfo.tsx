import { concertImg, moreViewIcon} from '../../assets/Images/index'
import styles from './BookingInfo.module.css'

export default function BookingInfo(){

    return(
        <div 
        className={styles.container}>
            <div
            className={styles.imgBox}>
                <img 
                className={styles.img}
                src={concertImg} alt="concertImg" />
            </div>
            
            <div
            className={styles.bookingInfo}>
                <div className={styles.concertName}>
                    오페라의 유령
                </div>
                <div className={styles.location}>
                    서울 
                </div>  
                <div className={styles.paymentDate}>
                    예매 일시
                </div>
                <div className={styles.seatCnt}>
                    9석
                </div>
                <div className={styles.paymentAmount}>
                    결제 금액
                </div>
                <div className={styles.period}>
                    2023.7.21 ~ 2023.11.19
                </div>
            </div>

            <div
            className={styles.moreView}>
                <img 
                    className={styles.img}
                    src={moreViewIcon} alt="moreViewIcon" />
            </div>
            
        </div>
    );
}