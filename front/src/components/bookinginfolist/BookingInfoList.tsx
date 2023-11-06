import styles from "./BookingInfoList.module.css"
import BookingInfo from "../bookinginfo/BookingInfo"

export default function BookingInfoList({props}){
    console.log(props)
    return(
        <div
            className={styles.container}>  
              
            {props?.tickets?.map((ticket)=>{
                return <div 
                className={styles.bookingInfo}
                key={ticket}
                >
                    <BookingInfo ticket={ticket} />
                </div>

            })}
         
        </div>
    )
}