import { useState } from 'react';
import styles from './Home.module.css'
import Search from '../components/search/Search.js'
import ConcertList from '../components/concertlist/ConcertList.js'
import Carousel from '../components/carousel/MyCarousel.js';
// import { useQuery } from 'react-query';
// import axios from 'axios';
// import {API_BASE_URL} from '../constants/index.ts';
// import {  BrowserRouter as Router, Route, Routes, Navigate  } from 'react-router-dom';

// interface ConcertListProps {
//     concertList: any; // concertList의 실제 타입으로 변경
//   }

// API 호출 함수의 반환 유형 정의
// interface DataItem {
//     id: number;
//     name: string;
//   }

// API 호출 함수
// const fetchData = async () => {
//   const response = await axios.get<DataItem[]>(`${API_BASE_URL}/concert`);
//   return response.data;
// };

function Home(){
    // const navigate = useNavigate();
    // const { concertList, isLoading, isError, error } = useQuery<DataItem[], Error>('data', fetchData);
    const [category,setCategory] = useState("reservationNow");

    // if (isLoading) {
    //     return <div>Loading...</div>;
    //   }
    
    // if (isError) {
    //     return <div>Error: {error.message}</div>;
    // }

    const toggleCategory = (categoryName:string) => {
        setCategory(categoryName)
    }

    return(        
        <div
        className={styles.container}>
            {/* 배너 이후에 캐러셀로 대체 */}
            <Carousel/>
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
                <ConcertList />

        </div>
    );
   

}

export default Home