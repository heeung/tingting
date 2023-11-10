import styles from "./ConcertReservation.module.css"
import Navbar from "../components/navbar/ReservationNavbar"
import { useLocation } from 'react-router-dom';
import axios from "axios";
import { API_BASE_URL } from "../constants";
import { useQuery } from "react-query";
import { useState, useEffect } from "react";
import SeatList from "../components/seatlist/SeatList";

import Lottie from 'lottie-react';
import { animationLoading } from '../assets/Images/index.js';
import SelectedSeat from '../components/selectedseat/SelectedSeat.js';



export default function ConcertReservation(){

    const location = useLocation();
    const {seq, concertSeq, holdDate} = location.state.schedule
    const {concertName} = location.state

    const [selectedSeat,setSelectedSeat] = useState([])
    const [concertHallSeq,SetConcertHallSeq] = useState(0)
    const [concertPattern,SetConcertPattern] = useState("")
    // const [nowSection, SetNowSection] = useState("")
    const [queryKey, setQueryKey] = useState(""); 


    const getSeatCnt = () => {
      return selectedSeat.length
    }

    const totalPrice = selectedSeat.reduce(function add(sum, currValue) {
      if(currValue.price === undefined){
        return 0
      }else{
        return sum + currValue.price;
      }
    }, 0);


    const fetchConcertType= async () => {
        const response = await axios.get(`${API_BASE_URL}/book/${concertSeq}`);
        SetConcertHallSeq(response.data.concertHallSeq)
        SetConcertPattern(response.data.pattern)
        // SetNowSection(response.data.pattern)
        setQueryKey("fetched")

    }

    const fetchSeats= async () => {
        const requestDto = {
            concertHallSeq:concertHallSeq,
            target:concertPattern
        }

        const response = await axios.get(`${API_BASE_URL}/book/${seq}/section`,{params:requestDto});
        return response.data;

    }
    
    useEffect(()=>{
        // 콘서트장 정보조회
        fetchConcertType()
    },[])

    const reservation = async () => {
      if(selectedSeat.length==0){
        return 
      }

      const seatSeqs = selectedSeat.map((seat)=>seat.concertSeatInfoSeq)
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

    // useQuery를 이용하는게 맞는가?, 콘서트장 정보조회
    // const { isLoading, isError, data, error } = useQuery("concertType", ()=>fetchConcertType(), {
    //     refetchOnWindowFocus: false, // react-query는 사용자가 사용하는 윈도우가 다른 곳을 갔다가 다시 화면으로 돌아오면 이 함수를 재실행합니다. 그 재실행 여부 옵션 입니다.
    //     // retry: 1, // 실패시 재호출 몇번 할지
    //     onSuccess: data => {
    //       // 성공시 호출
    //       console.log(data);
    //       // useQuery 이용하면 안써도..?
    //       SetConcertHallSeq(data.concertHallSeq)
    //       SetConcertPattern(data.pattern)
    //     },
    //     onError: e => {
    //       // 실패시 호출 (401, 404 같은 error가 아니라 정말 api 호출이 실패한 경우만 호출됩니다.)
    //       // 강제로 에러 발생시키려면 api단에서 throw Error 날립니다. (참조: https://react-query.tanstack.com/guides/query-functions#usage-with-fetch-and-other-clients-that-do-not-throw-by-default)
    //       console.log(e?.message);
    //     }
    //   });


      const { isLoading, data: seatData} = useQuery(queryKey, ()=>fetchSeats(), {
        refetchOnWindowFocus: false, // react-query는 사용자가 사용하는 윈도우가 다른 곳을 갔다가 다시 화면으로 돌아오면 이 함수를 재실행합니다. 그 재실행 여부 옵션 입니다.
        // retry: 1, // 실패시 재호출 몇번 할지
        // enabled : false,
        onSuccess: data => {
          // 성공시 호출
            console.log(data);
        },
        onError: e => {
          // 실패시 호출 (401, 404 같은 error가 아니라 정말 api 호출이 실패한 경우만 호출됩니다.)
          // 강제로 에러 발생시키려면 api단에서 throw Error 날립니다. (참조: https://react-query.tanstack.com/guides/query-functions#usage-with-fetch-and-other-clients-that-do-not-throw-by-default)
          console.log(e?.message);
        }
      });

    return(
        <div
        className={styles.container} >
            <Navbar holdDate={holdDate} concertName={concertName}/>
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
                className={styles.concertView}>
                {/* seat component */}
                <SeatList seats={seatData} setSelectedSeat={setSelectedSeat} selectedSeat={selectedSeat}/>
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
                  {/* max-height를 설정하고 overflow를 y로 설정해서 아래로 내려서 볼 수 있게 구성 */}
                  <div
                  className={styles.selectedSeat}>
                    {selectedSeat?.map((seat)=>{
                      return<div
                      className={styles['selected-seat-component']}
                      >
                        <SelectedSeat 
                        seat={seat?.seat} price={seat?.price}/>
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
                    onClick={()=>reservation()}
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


