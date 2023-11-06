import styles from "./ConcertReservation.module.css"
import Navbar from "../components/navbar/ReservationNavbar"

export default function ConcertReservation(){

    return(
        <div
        className={styles.container} >
            <Navbar/>
            <div 
            className={styles.body}>
                {/* 좌측 공연 섹션, 좌석 선택 페이지  */}
                <div 
                className={styles.concertView}>
                </div>
                
                {/* 우측 사용자 좌석 선택 확인 페이지 */}
                <div
                className={styles.selectedSeat}>

                </div>
            </div>

        </div>
    )
}


