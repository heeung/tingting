import styles from "./ConcertReservation.module.css"
import Navbar from "../components/navbar/ReservationNavbar"
import { useLocation } from 'react-router-dom';
import axios from "axios";
import { API_BASE_URL } from "../constants";
import { useQuery  } from "react-query";
import { useState, useEffect } from "react";
import SeatList from "../components/seatlist/SeatList";

import Lottie from 'lottie-react';
import { animationLoading } from '../assets/Images/index.js';
import SelectedSeat from '../components/selectedseat/SelectedSeat.js';
import Seat from "components/seat/Seat.js";

interface Seat{
  concertSeatInfoSeq:number;
  section:string;
  seat:string;  
  book:boolean;
  grade:string;
  price:number;
}


export default function ConcertReservation(){

    const location = useLocation();
    const {seq, concertSeq, holdDate} = location.state.schedule
    const {concertName} = location.state

    const [selectedSeat,setSelectedSeat] = useState<Seat[]>([])
    const [concertHallSeq,SetConcertHallSeq] = useState(0)
    const [nowSection, SetNowSection] = useState<string>("")
    const [queryKey, setQueryKey] = useState<string>(""); 


    const getSeatCnt = () => {
      return selectedSeat.length
    }

    const totalPrice = selectedSeat.reduce(function add(sum, currValue:Seat) {
      if(currValue.price === undefined){
        return 0
      }else{
        return sum + currValue.price;
      }
    }, 0);


    const fetchConcertType= async () => {
        const response = await axios.get(`${API_BASE_URL}/book/${concertSeq}`);
        SetConcertHallSeq(response.data.concertHallSeq)
        SetNowSection(response.data.pattern)
        setQueryKey("fetched")

    }

    const fetchSeats= async () => {
        const requestDto = {
            concertHallSeq:concertHallSeq,
            target:nowSection
        }

        const response = await axios.get(`${API_BASE_URL}/book/${seq}/section`,{params:requestDto});
        return response.data;

    }
    
    useEffect(()=>{
        // 콘서트장 정보조회
        fetchConcertType()
    },[])

    const reservation = async (selectedSeat:Seat[]) => {
      if(selectedSeat.length==0){
        return 
      }

      const seatSeqs = selectedSeat.map((seat:Seat)=>seat.concertSeatInfoSeq)
      const requestDto = {
        userSeq : 1
      }
      
      const response = await axios.post(`${API_BASE_URL}/book/${seq}/seat`,{seatSeqs:seatSeqs},{params:requestDto});
      if(response.data){
        setQueryKey(selectedSeat[0]?.seat)
        setSelectedSeat([])
      }
      return response.data
    }


      const { isLoading, data: seatData} = useQuery([nowSection,queryKey], ()=>fetchSeats(), {
        refetchOnWindowFocus: false, // react-query는 사용자가 사용하는 윈도우가 다른 곳을 갔다가 다시 화면으로 돌아오면 이 함수를 재실행합니다. 그 재실행 여부 옵션 입니다.
        retry: 3, // 실패시 재호출 몇번 할지
        // enabled : false,
        // onSuccess: data => {
        //     console.log(data);
        // },
        onError: e => {
            console.log(e);
        }
      });

    return(
        <div
        className={styles.container} >
            <Navbar holdDate={holdDate} concertName={concertName} section={nowSection} SetSection={SetNowSection}/>
            {isLoading
                ?
                <div>
                  <Lottie 
                  className={styles["loading-box"]}
                  animationData={animationLoading}/>
                </div>
                :
            <div 
            className={styles.body}>
                {/* 좌측 공연 섹션, 좌석 선택 페이지  */}
                
                <div
                className={styles["seat-grade-view"]}>
                  <div 
                  className={styles["seat-grade-box"]}>
                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 30 30" fill="none">
                       <rect width="30" height="30" fill="#EB7BFD"/>
                    </svg>
                    <div
                    className={styles["seat-grade-name"]}
                    >VIP</div>
                  </div>
                  <div
                  className={styles["seat-grade-box"]}>
                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 30 30" fill="none">
                        <rect width="30" height="30" fill="#90BCE5"/>
                    </svg>
                    <div
                    className={styles["seat-grade-name"]}
                    >일반</div>
                  </div>
                </div>
                
                
                <div 
                className={styles.concertView}>

                <div
                className={styles.stage}>
                  STAGE
                </div>

                <div
                className={styles["seat-list-box"]}>
                  <SeatList seats={seatData} setSelectedSeat={setSelectedSeat} selectedSeat={selectedSeat}/>
                </div>
                  <div
                  className={styles["seat-cnt-bar"]}
                  >
                    <span
                    className={styles["seat-cnt-comment"]}>
                      선택한 좌석 총 {(getSeatCnt() > 0) && <span className={styles["seat-cnt"]}>{getSeatCnt()}석</span>}이 선택되었습니다.
                    </span>
                  </div>
                </div>
                
                {/* 우측 사용자 좌석 선택 확인 페이지 */}
                <div
                className={styles.selectedSeatView}>
                  <div
                  className={styles.selectedSeat}>
                    {selectedSeat?.map((seat:Seat)=>{
                      return<div
                      key={seat?.concertSeatInfoSeq}
                      className={styles['selected-seat-component']}
                      >
                        <SelectedSeat 
                        seat={seat}/>
                      </div>
                    })}
                  </div>
                  {
                    (selectedSeat.length>0) &&
                    <div
                    className={styles['total-price']}>
                      총 결제금액 <span>{totalPrice}원</span>
                    </div>
                  }

                  
                  
                  <div
                  className={styles.buttonBox}>

                    {
                    (selectedSeat.length>0)
                    ?
                    <button
                    className={styles['active-button']}
                    onClick={()=>reservation(selectedSeat)}
                    >예매하기
                    </button>
                    :
                    <button
                    className={styles['inactive-button']}
                    >예매하기
                    </button>
                    }
                  </div>

                </div>
            </div>
            }
        </div>
    )
}


