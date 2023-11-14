import { useState,useEffect } from 'react';
import styles from './Home.module.css'
import Search from '../components/search/Search.js'
import ConcertList from '../components/concertlist/ConcertList.js'
import Carousel from '../components/carousel/MyCarousel.js';

import Lottie from 'lottie-react';
import { animationLoading } from '../assets/Images/index.js';
import { useNavigate } from 'react-router-dom'

// import { useRecoilState } from 'recoil';
// import { ConcertItemAtom } from '../recoil/ConcertItemAtom.tsx';
import {
    useQuery, 
  } from 'react-query';

import axios from 'axios';
// import { TEST_URL } from '../constants/index.js';
import {API_BASE_URL} from '../constants/index.ts';
// import {  BrowserRouter as Router, Route, Routes, Navigate  } from 'react-router-dom';


interface ConcertListProps {
    totalPage : number;
    currentPage : number;
    concerts: Concert[]
}

interface Concert {
    concertSeq : number;
    name : string;    
    holdOpenDate : string;
    holdCloseDate : string;
    imageUrl : string;
    concertHallName : string;
    concertHallCity : string;
}

interface ConcertListRequestDto {
    currentPage: number;
    itemCount: number;
    orderBy?: string;
    startDate?: string;
    endDate?: string;
    place?: string;
    searchWord?: string;
}



function Home(){

  const navigate = useNavigate()

  const [place,SetPlace] = useState("")
  const [searchWord,SetSearchWord] = useState("")
  const [isSearch,SetIsSearch] = useState(false)

  const [nowPage,setNowPage] = useState(1)
  const [category,setCategory] = useState("now");
  const [queryKey, setQueryKey] = useState('data');

  const toggleCategory = (categoryName:string) => {
      setCategory(categoryName)
      setQueryKey(categoryName); 
  }


  useEffect(()=>{
    if(isSearch==true){
      search()
      SetIsSearch(false)
    } 
  },[isSearch])
  const search = () => {
    navigate(`/search`, { state: { place, searchWord } });
  };
    // const navigate = useNavigate();
    // const [concertList, setConcertList] = useRecoilState(ConcertItemAtom) 

    // API 호출 함수
  const fetchData = async ({ pageParam = 1 }) => {

  const concertListRequest = {
    currentPage: pageParam,
    itemCount: 200,
    orderBy : category
  }

  const response = await axios.get(`${API_BASE_URL}/concerts`, {params:concertListRequest});
  return response.data;
};

    const { isLoading, isError, data, error } = useQuery([queryKey], ()=>fetchData({pageParam:nowPage}), {
        refetchOnWindowFocus: false, // react-query는 사용자가 사용하는 윈도우가 다른 곳을 갔다가 다시 화면으로 돌아오면 이 함수를 재실행합니다. 그 재실행 여부 옵션 입니다.
        retry: 1, // 실패시 재호출 몇번 할지
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
        className={styles.container}>
            {/* 배너 이후에 캐러셀로 대체 */}
            <Carousel/>
            {/* 검색 컴포넌트 */}
            <Search
            place={place} SetPlace={SetPlace} 
            searchWord={searchWord} SetSearchWord={SetSearchWord}
            isSearch={isSearch} SetIsSearch={SetIsSearch}
            />
                <div
                className={styles.toggleButtonBox}>

                    <div 
                    className={category=="now" ? `${styles.toggleButton} ${styles.on}` : `${styles.toggleButton} ${styles.off}`}
                    onClick={()=>toggleCategory("now")}
                    >
                        예매중 
                    </div>
                    <div 
                    className={category=="soon" ? `${styles.toggleButton} ${styles.on}` : `${styles.toggleButton} ${styles.off}`}
                    onClick={()=>toggleCategory("soon")}
                    >
                        예매임박
                    </div>
                </div>        


                {/* { isError && <div>Error: {error?.message}</div>} */}
                {/* 콘서트 리스트컴포넌트 */}

                {
                  !isLoading && data?.concerts!==undefined && <ConcertList props={data}/>
                }
                { (isLoading || data?.concerts===undefined) && 
                <div>
                  <Lottie 
                  className={styles["loading-box"]}
                  animationData={animationLoading}/>
                </div>
                }

        </div>
    );
   

}

export default Home