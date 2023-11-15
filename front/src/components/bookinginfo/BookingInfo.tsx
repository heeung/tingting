import { canceledTicket, cancelButton, cancelButtonRed } from '../../assets/Images/index'
import styles from './BookingInfo.module.css'
import { API_BASE_URL } from '../../constants'
import axios from 'axios'
import {useState} from "react"

interface Seat{
    ticketSeq:number;
    section:string;
    seat:string;
    price:number;
    name:string;
}

interface Ticket{
    ticketSeq:number;
    createdDate:string;
    deletedDate:string;
    totalPrice:number;
    seats:Seat[];
    concertSeq:number;
    name:string;
    holdDate:string;
    imageUrl:string
    concertHallName:string;
    concertHallCity:string;
}

interface BookingInfoProps {
    ticket: Ticket;
    setQueryKey: (key: string) => void;
  }

export default function BookingInfo({ticket,setQueryKey}:BookingInfoProps){
    const [isHover, setIsHover] = useState(false)
    
    const cancelTicket = async () => {
        const requestDto = {
            userSeq : 1
        }
        axios.delete(`${API_BASE_URL}/book/${ticket.ticketSeq}`,{params:requestDto})
        .then(response => {
            setQueryKey(ticket.ticketSeq.toString())
            return response.data
        })
        //response.message가 true면 예약 취소 완료 아니면 이미 취소된 공연
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
                    {ticket.seats ? ticket?.seats.length : 0}석
                </div>
                <div className={styles.paymentAmount}>
                    {ticket.totalPrice}원
                </div>
                <div className={styles.period}>
                    {ticket.createdDate && ticket?.createdDate.slice(0,10)} ~ {ticket.deletedDate && ticket?.deletedDate.slice(0,10)}
                </div>
            </div>

            {/* <div
            className={styles.moreView}>
                <img 
                    className={styles.img}
                    src={moreViewIcon} alt="moreViewIcon" />
            </div> */}
            

            {
                (ticket.deletedDate !== null) 
                ?
                <div>
                    <img 
                        className={styles.img}
                        src={canceledTicket} alt="canceledTicket" />
                        <div className={styles["cancel-comment"]}>
                            취소됨
                        </div>
                </div>
                :
                isHover
                ?
                <div
                className={styles['cancel-button']}
                onMouseEnter={()=>setIsHover(true)}
                onMouseLeave={()=>setIsHover(false)}
                onClick={cancelTicket}>
                    <img 
                    className={styles.img}
                    src={cancelButton} alt="cancelButton" />
                    <div className={styles["cancel-comment"]}>
                            예약취소
                        </div>
                </div>
                :
                <div
                className={styles['cancel-button']}
                onMouseEnter={()=>setIsHover(true)}
                onMouseLeave={()=>setIsHover(false)}
                onClick={cancelTicket}>
                    <img 
                    className={styles.img}
                    src={cancelButtonRed} alt="cancelButtonRed" />
                    <div className={styles["cancel-comment"]}>
                            예약취소
                        </div>
                </div>
            }

            
        </div>
    );
}