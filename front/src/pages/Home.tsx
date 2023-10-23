import React, { useState } from 'react';
import { bannerImg } from '../assets/Images/index.js'
import styles from './Home.module.css'
import Search from '../components/search/Search.js'
import ConcertList from '../components/concertlist/ConcertList.js'
function Home(){
    const [category,setCategory] = useState("reservationNow");

    const toggleCategory = (categoryName:string) => {
        setCategory(categoryName)
    }
    return(        
        <div
        className={styles.container}>
            {/* 배너 이후에 캐러셀로 대체 */}
            <img
            className={styles.banner}
            src={bannerImg} alt="loginBackground" />
            {/* 검색 컴포넌트 */}
            <Search/>
                <div
                className={styles.toggleButtonBox}>
                    <div 
                    className={category=="reservationNow" ? `${styles.toggleButton} ${styles.on}` : `${styles.toggleButton} ${styles.off}`}
                    onClick={()=>toggleCategory("reservationNow")}
                    >
                        예매중 
                    </div>
                    <div 
                    className={category=="reservationImminent" ? `${styles.toggleButton} ${styles.on}` : `${styles.toggleButton} ${styles.off}`}
                    onClick={()=>toggleCategory("reservationImminent")}
                    >
                        예매임박
                    </div>
                </div>
                {/* 콘서트 리스트컴포넌트 */}
                <ConcertList/>
        </div>
    );
   

}

export default Home