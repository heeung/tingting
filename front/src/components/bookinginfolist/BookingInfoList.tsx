import styles from "./BookingInfoList.module.css"
import BookingInfo from "../bookinginfo/BookingInfo"
import {deactivatedLogo} from '../../assets/Images/index'
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
    props: {
        tickets:Ticket[];
    }
    setQueryKey: (key: string) => void;
  }

export default function BookingInfoList({props,setQueryKey}:BookingInfoProps){
    const tickets = props?.tickets
    
    return(
        <div
            className={styles.container}>  
            {tickets && (tickets.length > 0) 
            ? 
              
            (tickets?.map((ticket)=>{
                return <div 
                className={styles.bookingInfo}
                key={ticket?.ticketSeq}
                >
                    <BookingInfo ticket={ticket} setQueryKey={setQueryKey} />
                </div>

            }))
            
            :
            ( 
                <div
                className={styles['comment-background']}
                >
                  <div>
                  <img 
                  className={styles.img}
                  src={deactivatedLogo} alt="deactivatedLogo" />
                  </div>
                  {
                    <div
                    className={styles.comment}
                    >예약한 공연이 없습니다</div>            
                  }
                </div>
              )


        }
         
        </div>
    )
}