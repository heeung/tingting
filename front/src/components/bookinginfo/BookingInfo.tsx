import { concertImg, moreViewIcon} from '../../assets/Images/index'
import styles from './BookingInfo.module.css'

export default function BookingInfo({ticket}){
    console.log(ticket)
    return(
        <div 
        className={styles.container}>
            <div
            className={styles.imgBox}>
                <img 
                className={styles.img}
                src={ticket.imageUrl} alt="concertImg" />
            </div>
            
            <div
            className={styles.bookingInfo}>
                <div className={styles.concertName}>
                    {ticket.name}
                </div>
                <div className={styles.location}>
                    {ticket.concertHallCity}
                </div>  
                <div className={styles.paymentDate}>
                    {ticket.createdDate}
                </div>
                <div className={styles.seatCnt}>
                    9석
                </div>
                <div className={styles.paymentAmount}>
                    {ticket.totalPrice}원
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