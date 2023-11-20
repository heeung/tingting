import styles from "./ConcertDetail.module.css"
import {likeButton, cancelLikeButton} from "../assets/Images/" 
import {useState} from "react"
import ScheduleList from "../components/scheduleList/ScheduleList"
import { useParams } from 'react-router-dom';
import { useQuery } from 'react-query';
import axios from "axios";
import {API_BASE_URL} from '../constants/index.ts';

import Lottie from 'lottie-react';
import { animationLoading } from '../assets/Images/index.js';

import { useRecoilValue } from 'recoil';
import { userSeqState } from "../recoil/UserAtom.tsx";


interface Performer{
    seq : number;
    performerName : string;
    performerImageUrl : string;
}

export default function ConcertDetail(){

    const params = useParams();
    const userSeq = useRecoilValue(userSeqState);
    
    const [isHover, setIsHover] = useState(false)
    const [isLike, setIsLike] = useState(false)
    const concertSeq = params.concertseq
    
    const isSameConcert = () => {
        return concertSeq == data.concertSeq
    }

    const fetchDetailData = async () => {

        const concertDetailRequest = {
            userSeq : userSeq
        }
        const response = await axios.get(`${API_BASE_URL}/concerts/${concertSeq}`, {params:concertDetailRequest});
        return response.data;
      };

    const { isLoading, data } = useQuery("data", fetchDetailData, {
        refetchOnWindowFocus: false, // react-query는 사용자가 사용하는 윈도우가 다른 곳을 갔다가 다시 화면으로 돌아오면 이 함수를 재실행합니다. 그 재실행 여부 옵션 입니다.
        onSuccess: data => {
          // 성공시 호출
          setIsLike(data.favorite)
        },
      });

    const toggleIsLike = async () => {
        if(userSeq==null){
            return
        }

        const likeRequest = {
            concertSeq: concertSeq,
            userSeq : userSeq
        }
        const response = await axios.post(`${API_BASE_URL}/concerts/favorite`, likeRequest);
        setIsLike(response.data.favorite)
    }

    return(
        <div
        className={styles.container}>
            {
                isLoading 
                || !isSameConcert()
                
                ?
                <div>
                <Lottie 
                className={styles["loading-box"]}
                animationData={animationLoading}/>
              </div>
                :
            <div
            className={styles.detailContainer}>
                <div
                className={styles.likeButton}>
                    {
                        isLike 
                        ?
                        <img 
                        src={cancelLikeButton} alt="cancelLikeButton"
                        onClick={toggleIsLike} />
                        :
                        <img 
                        src={likeButton} alt="likeButton" 
                        onClick={toggleIsLike} />
                    }
                </div>
                <div
                className={styles.concertBox}>
                    <div
                    className={styles.concertImg}
                    >
                        <img 
                        className={styles.img}
                        src={data?.imageUrl} alt="concertImg" />
                    </div>
                    <div
                    className={styles.concertInfoBox}
                    >
                        <div
                        className={styles.concertName}>
                            {data?.name}({data?.concertHallCity
})
                        </div>
                        <div
                        className={styles.concertInfo}
                        >{data?.info}</div>
                        <div
                        className={styles.concertDetail}>
                            <div>
                                예약 시작 : {data?.bookOpenDate}
                            </div>
                            <div>
                                예약 끝 : {data?.bookCloseDate}
                            </div>
                            <div>
                                공연 시작 : {data?.holdOpenDate}
                            </div>
                            <div>
                                공연 끝 : {data?.holdCloseDate}
                            </div>
                            <div>
                                공연 장소 : {data?.concertHallCity} / {data?.concertHallName}
                            </div>
                            <div className={styles.host}>
                                출연자 : {data?.performers?.map((performer:Performer)=>{
                                    return(
                                        <div
                                        className={styles['host-list']}
                                        key={performer?.seq}
                                        >
                                            <span
                                            onMouseEnter={()=>setIsHover(true)}
                                            onMouseLeave={()=>setIsHover(false)}
                                            className={styles['host-name']}
                                            >
                                                {performer?.performerName}
                                            </span>
                                            {
                                                isHover &&
                                                <div 
                                                className={styles["host-image"]}>
                                                    <img src={performer?.performerImageUrl} alt="" />
                                                </div>
                                            }

                                        </div>

                                    )
                                })}
                            </div>
                        </div>
                    </div>
                </div>
                
                <div
                className={styles.concert}>
                    <ScheduleList 
                    concertDetails={data?.concertDetails}
                    concertName={data?.name}
                    />
                </div>

            </div>
            }


        </div>
    )
}