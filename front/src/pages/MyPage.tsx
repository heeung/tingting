import styles from "./MyPage.module.css"
import {kakakoConnection} from "../assets/Images/index.ts"
import { useState } from 'react'
import BookingInfoList from "../components/bookinginfolist/BookingInfoList.tsx"
import ConcertList from "../components/concertlist/ConcertList.tsx"

export default function MyPage(){

    const [category,setCategory] = useState("reservations");

    const toggleCategory = (categoryName:string) => {
        setCategory(categoryName)
    }
    
    return(
        <div
        className={styles.container}>
            <div 
            className={styles.userInfo}>
                <div 
                className={styles.id_box}> 
                    <div 
                    className={styles.id}>
                        <h2>
                        아이디 : ssafy.ssafy.com
                        </h2>
                    </div>
                    <div
                    className={styles.connection}>
                        <img src={kakakoConnection} alt="kakaoConnection" />
                    </div>
                </div>
                <div
                className={styles.point}> 
                    <h2>
                    TT Money : 10000000
                    </h2>
                </div>
            </div>

            <div
            className={styles.toggleButtonBox}>
                <div 
                className={category=="reservations" ? `${styles.toggleButton} ${styles.on}` : `${styles.toggleButton} ${styles.off}`}
                onClick={()=>toggleCategory("reservations")}
                >
                    예매내역
                </div>
                <div 
                className={category=="favoritePerformances" ? `${styles.toggleButton} ${styles.on}` : `${styles.toggleButton} ${styles.off}`}
                onClick={()=>toggleCategory("favoritePerformances")}
                >
                    찜한 공연
                </div>
            </div>

            {
                category == "reservations" 
                ?
                <BookingInfoList/>
                :
                <ConcertList/>
            }
        </div>
    )
}