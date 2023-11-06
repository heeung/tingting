import styles from "./ConcertDetail.module.css"
import {concertImg, likeButton, cancelLikeButton} from "../assets/Images/" 
import {useState} from "react"
import ScheduleList from "../components/scheduleList/ScheduleList"
import { useParams } from 'react-router-dom';
import { useQuery } from 'react-query';
import axios from "axios";
import {API_BASE_URL} from '../constants/index.ts';



export default function ConcertList(){

    const params = useParams();
    
    const [isLike, setIsLike] = useState(false)
    const concertSeq = params.concertseq
    
    const fetchData = async () => {

        const concertDetailRequest = {
            userSeq : 1
        }
    
        const response = await axios.get(`${API_BASE_URL}/concerts/${concertSeq}`, {params:concertDetailRequest});
        return response.data;
      };

    const { isLoading, isError, data, error } = useQuery("data", fetchData, {
        refetchOnWindowFocus: false, // react-query는 사용자가 사용하는 윈도우가 다른 곳을 갔다가 다시 화면으로 돌아오면 이 함수를 재실행합니다. 그 재실행 여부 옵션 입니다.
        retry: 100, // 실패시 재호출 몇번 할지
        onSuccess: data => {
          // 성공시 호출
          setIsLike(data.favorite)
        
        },
        onError: e => {
          // 실패시 호출 (401, 404 같은 error가 아니라 정말 api 호출이 실패한 경우만 호출됩니다.)
          // 강제로 에러 발생시키려면 api단에서 throw Error 날립니다. (참조: https://react-query.tanstack.com/guides/query-functions#usage-with-fetch-and-other-clients-that-do-not-throw-by-default)
          console.log(e?.message);
        }
      });

    const toggleIsLike = async (isLike:boolean) => {
        const likeRequest = {
            concertSeq: concertSeq,
            userSeq : 1
        }
        const response = await axios.post(`${API_BASE_URL}/concerts/favorite`, likeRequest);
        setIsLike(response.data.favorite)
        if (response.data.favorite){
            console.log("현재 찜 상태입니다")
        }else{
            console.log("현재 찜 안한 상태입니다")
        }
    }

    return(
        <div
        className={styles.container}>
            <div
            className={styles.detailContainer}>
                <div
                className={styles.likeButton}>
                    {
                        isLike 
                        ?
                        <img 
                        // className={styles.img}
                        src={cancelLikeButton} alt="cancelLikeButton"
                        onClick={toggleIsLike} />
                        :
                        <img 
                        // className={styles.img}
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
                            <div>
                                출연자 : {data?.performers?.map((performer)=>{
                                    return(
                                    <span key={performer?.seq}>
                                        {performer?.performerName}
                                    </span>
                                    )
                                })}
                            </div>
                        </div>
                    </div>
                </div>
                
                
                <div
                className={styles.concert}>
                    <ScheduleList concertDetails={data?.concertDetails}/>
                </div>

            </div>


        </div>
    )
}