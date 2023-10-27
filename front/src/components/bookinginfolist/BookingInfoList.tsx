import styles from "./BookingInfoList.module.css"
import BookingInfo from "../bookinginfo/BookingInfo"

export default function BookingInfoList(){
    const lst = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
    return(
        <div
            className={styles.container}>  
              
            {lst.map((item:number)=>{
                return <div 
                className={styles.bookingInfo}
                key={item}
                >
                    <BookingInfo/>
                </div>

            })}
         
        </div>
    )
}