import {cancelTiket} from '../../assets/Images/index'
import styles from './BookingInfo.module.css'
import { API_BASE_URL } from '../../constants'
import axios from 'axios'


export default function BookingInfo({ticket}){
    console.log(ticket)

    const cancelTicket = async () => {
        const requestDto = {
            userSeq : 1
        }

        const response = await axios.delete(`${API_BASE_URL}/book/${ticket.ticketSeq}`,{params:requestDto});
        //response.message가 true면 예약 취소 완료 아니면 이미 취소된 공연
        return response.data;

    }


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
                    {ticket.seats.length}석
                </div>
                <div className={styles.paymentAmount}>
                    {ticket.totalPrice}원
                </div>
                <div className={styles.period}>
                    {ticket.createdDate.slice(0,10)} ~ {ticket.deletedDate.slice(0,10)}
                </div>
            </div>

            {/* <div
            className={styles.moreView}>
                <img 
                    className={styles.img}
                    src={moreViewIcon} alt="moreViewIcon" />
            </div> */}
            
            <div
            className={styles["cancel-button"]}
            onClick={cancelTicket}>
                <img 
                    className={styles.img}
                    src={cancelTiket} alt="cancelTiket" />
        
                    {(ticket.deletedDate !== null)
                    && 
                    <div className={styles["cancel-comment"]}>
                        취소됨
                    </div>
                    }
            </div>

            
        </div>
    );
}