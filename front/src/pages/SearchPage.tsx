import styles from './SearchPage.module.css'
import Search from '../components/search/Search'
import ConcertList from '../components/concertlist/ConcertList'
import axios from 'axios';
import {API_BASE_URL} from '../constants/index.ts';
import { useInfiniteQuery } from 'react-query';
import { useState, useEffect, useRef, useCallback  } from 'react';
import { useLocation } from 'react-router-dom';
import Lottie from 'lottie-react';
import { animationLoading } from '../assets/Images/index.js';


// interface Concert {
//   concertSeq: number;
//   name: string;
//   holdOpenDate: string;
//   holdCloseDate: string;
//   imageUrl: string;
//   concertHallName: string;
//   concertHallCity: string;
// }

// interface Page{
//   totalPage	: number;
//   currentPage : number;
//   concerts: Concert[];
// }

// interface ConcertListResponse {
//   pageParams: number[];
//   pages: {
//     totalPage: number;
//     currentPage: number;
//     concerts: Concert[]; // 'concerts' 속성 추가
//   }[];
// }

type ConcertListRequest = {
  currentPage: number;
  itemCount: number;
  orderBy?: string;
  searchWord?:string;
  place?:string;
};


export default function SearchPage(){
  
  const [maxPage,SetMaxPage] = useState(1)
  const [place,SetPlace] = useState<string>("")
  const [searchWord,SetSearchWord] = useState<string>("")
  const [isSearch,SetIsSearch] = useState<boolean>(false)
  const location = useLocation();
  
  useEffect(()=>{
    const state = location.state as { place: string; searchWord: string };
    let searchFlag = false
    if(state==null){
      return 
    }
    if(state.place!==null){
      SetPlace(state.place)
      searchFlag = true
    }
    if(state.searchWord!==null){
      SetSearchWord(state.searchWord)
      searchFlag = true
    }
    if (searchFlag == true){
      SetIsSearch(true)
    }

  
  },[])

    // API 호출 함수
  const fetchData = async (page=1) => {

    let concertListRequest:ConcertListRequest = {
      currentPage: page,
      itemCount: 60,
    }

    if(searchWord=="" && place==""){
      concertListRequest = {
        ...concertListRequest,
        orderBy :"now"
      }
    }

    if(searchWord!=""){
      concertListRequest = {
        ...concertListRequest,
        searchWord : searchWord
      }
    }

    if(place!=""){
      concertListRequest = {
        ...concertListRequest,
        place : place
      }
    }
  
    const response = await axios.get(`${API_BASE_URL}/concerts`, {params:concertListRequest});
    return response.data;
  };



  const observerElem = useRef(null);

  const { data, isLoading, hasNextPage, fetchNextPage, isFetchingNextPage } =
    useInfiniteQuery(
      [isSearch],
      ({ pageParam = 1 }) => fetchData(pageParam),
      {
        getNextPageParam: (_lastPage, pages) => {
          if (pages.length < maxPage) {
            return pages.length + 1;
          } else return undefined;
        },
        onSuccess: res => {
              SetMaxPage(res.pages[0].totalPage)
            },
      },
      
    );

  const handleObserver = useCallback(
    (entries: IntersectionObserverEntry[]) => {
      const [target] = entries;
      if (target.isIntersecting && hasNextPage) {
        fetchNextPage();
      }
    },
    [fetchNextPage, hasNextPage]
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
  }, [fetchNextPage, hasNextPage, handleObserver]);

  const allPagesContent = data?.pages?.reduce(
    (acc, page) => {
      // 각 페이지의 내용을 합친다.
      if (page && page.concerts) {
        acc.concerts = acc.concerts.concat(page.concerts);
      }
      // 마지막 페이지의 totalPage와 currentPage만 유지한다.
      if (page === data.pages[data.pages.length - 1]) {
        acc.totalPage = page.totalPage;
        acc.currentPage = page.currentPage;
      }
      return acc;
    },
    { concerts: [], totalPage: 0, currentPage: 0 } // 초기값 설정
  );

    return(
        <div className={styles.container}>
            <div
            className={styles.searchBar}>
                <Search 
                place={place} SetPlace={SetPlace} 
                searchWord={searchWord} SetSearchWord={SetSearchWord}
                isSearch={isSearch} SetIsSearch={SetIsSearch}
                />
            </div>
                {
                  !isLoading && 
                  <div>
                  <ConcertList props={allPagesContent} searchWord={searchWord} />
                  </div>
                }
                { isLoading && 
                <div>
                  <Lottie 
                  className={styles["loading-box"]}
                  animationData={animationLoading}/>
                </div>
                }
              <div className={styles["loader"]} ref={observerElem}>
                {isFetchingNextPage && hasNextPage ?                 
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
    )
}