import styles from "./MyPage.module.css"
import {kakakoConnection} from "../assets/Images/index.ts"
import { useState } from 'react'
import BookingInfoList from "../components/bookinginfolist/BookingInfoList.tsx"
import ConcertList from "../components/concertlist/ConcertList.tsx"
// import { useRecoilState } from "recoil"
import axios from "axios"
import { API_BASE_URL } from "../constants/index.ts"
import { useQuery } from "react-query"

import Lottie from 'lottie-react';
import { animationLoading } from '../assets/Images/index.js';


export default function MyPage(){

    const [category,setCategory] = useState("reservations");
    const [queryKey, setQueryKey] = useState('');
    
    // API 호출 함수
    
    const fetchPoint = async () => {
        const userSeq = 1
        const response = await axios.get(`${API_BASE_URL}/users/${userSeq}/point`);
        return response.data.point;
    }
    
    const fetchLikedData = async () => {
        const userSeq = 1
        const concertListRequest = {
        currentPage: 1,
        itemCount: 100,
        }
        const response = await axios.get(`${API_BASE_URL}/users/${userSeq}/favorite`,{params:concertListRequest});
        return response.data;
    };


    const fetchTicketData = async () => {
    const userSeq = 1
    const concertListRequest = {
        currentPage: 1,
        itemCount: 100,
    }
    const response = await axios.get(`${API_BASE_URL}/users/${userSeq}/ticket`,{params:concertListRequest});
    return response.data;
    };

    const { isLoading : isLikedLoading, data : likedData } = useQuery("likedData", fetchLikedData, {
        refetchOnWindowFocus: false,
        // onSuccess: data => {
        //   console.log(data);
        // },
        onError: e => {
          console.log(e);
        }
      });

    const { isLoading: isticketLoading, data : ticketData } = useQuery([queryKey], fetchTicketData, {
        refetchOnWindowFocus: false,
        // onSuccess: data => {
        //   console.log(data);
        // },
        onError: e => {
          console.log(e);
        }
      });


      const { isLoading: ispointLoading, data : pointData } = useQuery([ticketData], fetchPoint, {
        refetchOnWindowFocus: false,
        // onSuccess: data => {
        //   console.log(data);
        // },
        onError: e => {
          console.log(e);
        }
      });

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
                    TT Money : {!ispointLoading && pointData}
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
                category === "reservations" 
                ?
                <div>
                    <div>
                        { 
                        <BookingInfoList props={ticketData} setQueryKey={setQueryKey}/>
                        }
                    </div>
                    {(isticketLoading || ticketData?.tickets === undefined) &&
                    <Lottie 
                    className={styles["loading-box"]}
                    animationData={animationLoading}/>}
    
                </div>
                
                :
                <div>
                    <div>
                        {
                        <ConcertList props={likedData} searchWord=""/>
                        }
                    </div>
                    {(isLikedLoading || likedData?.concerts===undefined) &&
                    <Lottie 
                    className={styles["loading-box"]}
                    animationData={animationLoading}/>}
                </div>
            }

        </div>
    )
}