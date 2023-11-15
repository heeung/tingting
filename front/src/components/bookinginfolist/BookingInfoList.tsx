import styles from "./BookingInfoList.module.css"
import BookingInfo from "../bookinginfo/BookingInfo"

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
    return(
        <div
            className={styles.container}>  
              
            {props?.tickets?.map((ticket)=>{
                return <div 
                className={styles.bookingInfo}
                key={ticket?.ticketSeq}
                >
                    <BookingInfo ticket={ticket} setQueryKey={setQueryKey} />
                </div>

            })}
         
        </div>
    )
}