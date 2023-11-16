import styles from "./MyPage.module.css"
import {kakakoConnection} from "../assets/Images/index.ts"
import { useState, useEffect, useRef, useCallback  } from 'react';
import BookingInfoList from "../components/bookinginfolist/BookingInfoList.tsx"
import ConcertList from "../components/concertlist/ConcertList.tsx"
// import { useRecoilState } from "recoil"
import axios from "axios"
import { API_BASE_URL } from "../constants/index.ts"
import { useInfiniteQuery, useQuery } from "react-query"

import Lottie from 'lottie-react';
import { animationLoading } from '../assets/Images/index.js';

import { useRecoilValue } from 'recoil';
import { userEmailState,userSeqState } from "../recoil/UserAtom.tsx";


export default function MyPage(){

    const userEmail = useRecoilValue(userEmailState);
    const userSeq = useRecoilValue(userSeqState);

    const [category,setCategory] = useState("reservations");
    const [queryKey, setQueryKey] = useState('');
    const [maxPage,SetMaxPage] = useState(1)
    
    
    // API 호출 함수
    
    const fetchPoint = async () => {
        const response = await axios.get(`${API_BASE_URL}/users/${userSeq}/point`);
        return response.data.point;
    }
    
    const fetchLikedData = async (page=1) => {
        const concertListRequest = {
        currentPage: page,
        itemCount: 20,
        }
        const response = await axios.get(`${API_BASE_URL}/users/${userSeq}/favorite`,{params:concertListRequest});
        return response.data;
    };


    const fetchTicketData = async (page=1) => {
    const concertListRequest = {
        currentPage: page,
        itemCount: 20,
    }
    const response = await axios.get(`${API_BASE_URL}/users/${userSeq}/ticket`,{params:concertListRequest});
    return response.data;
    };


    const toggleCategory = (categoryName:string) => {
        setCategory(categoryName)
    }
    
    const observerElem = useRef(null);

    const { data:ticketData, isLoading:isticketLoading, hasNextPage:hasNextTicketPage, fetchNextPage:fetchNextTicketPage, isFetchingNextPage:isFetchingNextTicketPage } =
      useInfiniteQuery(
        ['ticketData',queryKey],
        ({ pageParam = 1 }) => fetchTicketData(pageParam),
        {
          getNextPageParam: (_lastPage, pages) => {
            if (pages.length < maxPage) {
              return pages.length + 1;
            } else return undefined;
          },
          onSuccess: res => {
                SetMaxPage(res.pages[0].totalPage)
              } 
        },
        
      );

    const { data:likedData, isLoading:isLikedLoading, hasNextPage:hasNextLikedPage, fetchNextPage:fetchNextLikedPage, isFetchingNextPage:isFetchingNextLikedPage } =
      useInfiniteQuery(
        ['likedData',queryKey],
        ({ pageParam = 1 }) => fetchLikedData(pageParam),
        {
          getNextPageParam: (_lastPage, pages) => {
            if (pages.length < maxPage) {
              return pages.length + 1;
            } else return undefined;
          },
          onSuccess: res => {
                SetMaxPage(res.pages[0].totalPage)
              }
        },
        
      );
  

        const { isLoading: ispointLoading, data : pointData } = useQuery([ticketData], fetchPoint, {
        refetchOnWindowFocus: false,
        });

    const handleObserver = useCallback(
      (entries: IntersectionObserverEntry[]) => {
        const [target] = entries;
        if (target.isIntersecting && hasNextLikedPage) {
          fetchNextLikedPage();
        }
        if (target.isIntersecting && hasNextTicketPage) {
          fetchNextTicketPage();
        }
      },
      [fetchNextTicketPage, hasNextTicketPage,fetchNextLikedPage, hasNextLikedPage]
    );
  
  
    useEffect(() => {
      const element = observerElem.current;
  
      const options = {
        root: null,
        rootMargin: "0px",
        threshold: 1,
  
      };
  
      const observer = new IntersectionObserver(handleObserver, options);
      if (element) observer.observe(element);
      return () => {
        if (element) observer.unobserve(element);
      };
    }, [fetchNextTicketPage, hasNextTicketPage,fetchNextLikedPage, hasNextLikedPage, handleObserver]);
  
    const allPagesContent = likedData?.pages?.reduce(
        (acc, page) => {
          // 각 페이지의 내용을 합친다.
          if (page && page.concerts) {
            acc.concerts = acc.concerts.concat(page.concerts);
          }
          // 마지막 페이지의 totalPage와 currentPage만 유지한다.
          if (page === likedData.pages[likedData.pages.length - 1]) {
            acc.totalPage = page.totalPage;
            acc.currentPage = page.currentPage;
          }
          return acc;
        },
        { concerts: [], totalPage: 0, currentPage: 0 } // 초기값 설정
      );

    const allTicketsContent = ticketData?.pages?.reduce(
        (acc, page) => {
          // 각 페이지의 내용을 합친다.
          if (page && page.tickets) {
            acc.tickets = acc.tickets.concat(page.tickets);
          }
          // 마지막 페이지의 totalPage와 currentPage만 유지한다.
          if (page === ticketData.pages[ticketData.pages.length - 1]) {
            acc.totalPage = page.totalPage;
            acc.currentPage = page.currentPage;
          }
          return acc;
        },
        { tickets: [], totalPage: 0, currentPage: 0 } // 초기값 설정
      );

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
                        아이디 : {userEmail}
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
                <div
                className={styles.w100}>
                    <div
                    className={styles.w100}>
                        { 
                        <BookingInfoList props={allTicketsContent} setQueryKey={setQueryKey}/>
                        }
                    </div>
                    {(isticketLoading||isFetchingNextTicketPage) &&
                    <Lottie 
                    className={styles["loading-box"]}
                    animationData={animationLoading}/>}

                    <div className={styles["loader"]} ref={observerElem}>
                        {isFetchingNextTicketPage && hasNextTicketPage 
                        ?                 
                        <div>
                        <Lottie 
                        className={styles["loading-box"]}
                        animationData={animationLoading}/>
                        </div> 
                        : 
                        <div>
                        </div>}
                    </div>
                </div>
                
                :
                <div
                className={styles.w100}>
                    <div
                    className={styles.w100}>
                        {
                        <ConcertList props={allPagesContent} searchWord="" isLiked={true}/>
                        }
                    </div>
                    {(isLikedLoading||isFetchingNextLikedPage)  &&
                    <Lottie 
                    className={styles["loading-box"]}
                    animationData={animationLoading}/>}

                    <div className={styles["loader"]} ref={observerElem}>
                        {isFetchingNextLikedPage && hasNextLikedPage 
                        ?                 
                        <div>
                        <Lottie 
                        className={styles["loading-box"]}
                        animationData={animationLoading}/>
                        </div> 
                        : 
                        <div>
                        </div>}
                    </div>
                    
                </div>
            }

        </div>
    )
}