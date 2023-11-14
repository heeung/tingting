import styles from "./BookingInfoList.module.css"
import BookingInfo from "../bookinginfo/BookingInfo"

export default function BookingInfoList({props,setQueryKey}){
    console.log(setQueryKey)
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