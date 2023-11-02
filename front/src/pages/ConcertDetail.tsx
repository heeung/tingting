import styles from "./ConcertDetail.module.css"
import {concertImg, likeButton, cancelLikeButton} from "../assets/Images/" 
import {useState} from "react"
import ScheduleList from "../components/scheduleList/scheduleList"


export default function ConcertList(){
    const [isLike, setIsLike] = useState(false)

    const toggleIsLike = () => {
        setIsLike(!isLike)
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
                        src={concertImg} alt="concertImg" />
                    </div>
                    <div
                    className={styles.concertInfoBox}
                    >
                        <div
                        className={styles.concertName}>
                            콘서트 이름(장소)
                        </div>
                        <div
                        className={styles.concertInfo}
                        >콘서트 설명 콘서트 설명 콘서트 설명 콘서트 설명 콘서트 설명 콘서트 설명
                        콘서트 설명 콘서트 설명 콘서트 설명 콘서트 설명 콘서트 설명 콘서트 설명</div>
                        <div
                        className={styles.concertDetail}>
                            <div>
                                콘서트 디테일 설명 / 콘서트 디테일 설명
                            </div>
                            <div>
                                콘서트 디테일 설명 / 콘서트 디테일 설명
                            </div>
                            <div>
                                콘서트 디테일 설명 / 콘서트 디테일 설명
                            </div>
                            <div>
                                콘서트 디테일 설명 / 콘서트 디테일 설명
                            </div>
                            <div>
                                콘서트 디테일 설명 / 콘서트 디테일 설명
                            </div>
                        </div>
                    </div>
                </div>
                
                <div></div>
                
                <div
                className={styles.concert}>
                    <ScheduleList/>
                </div>

            </div>


        </div>
    )
}