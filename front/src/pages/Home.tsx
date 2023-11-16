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
import {API_BASE_URL} from '../constants/index.ts';
// import {  BrowserRouter as Router, Route, Routes, Navigate  } from 'react-router-dom';


// interface ConcertListProps {
//     totalPage : number;
//     currentPage : number;
//     concerts: Concert[]
// }

// interface Concert {
//     concertSeq : number;
//     name : string;    
//     holdOpenDate : string;
//     holdCloseDate : string;
//     imageUrl : string;
//     concertHallName : string;
//     concertHallCity : string;
// }

// interface ConcertListRequestDto {
//     currentPage: number;
//     itemCount: number;
//     orderBy?: string;
//     startDate?: string;
//     endDate?: string;
//     place?: string;
//     searchWord?: string;
// }



function Home(){

  const navigate = useNavigate()

  const [place,SetPlace] = useState("")
  const [searchWord,SetSearchWord] = useState("")
  const [isSearch,SetIsSearch] = useState(false)

  // const [nowPage,setNowPage] = useState(1)
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
  const fetchData = async ({ pageParam=1 }) => {
  
  const concertListRequest = {
    currentPage: pageParam,
    itemCount: 200,
    orderBy : category
  }

  const response = await axios.get(`${API_BASE_URL}/concerts`, {params:concertListRequest});
  return response.data;
};

    const { isLoading, data } = useQuery([queryKey], ()=>fetchData({pageParam:1}), {
        refetchOnWindowFocus: false, // react-query는 사용자가 사용하는 윈도우가 다른 곳을 갔다가 다시 화면으로 돌아오면 이 함수를 재실행합니다. 그 재실행 여부 옵션 입니다.
        retry: 1, // 실패시 재호출 몇번 할지
        // onSuccess: data => {
        //   console.log(data);
        // },
        onError: e => {
          console.log(e);
        }
      });

    return(        
        <div
        className={styles.container}>
            <Carousel/>
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

                {
                  !isLoading && data?.concerts!==undefined && <ConcertList props={data} searchWord=""/>
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