import styles from './SearchPage.module.css'
import Search from '../components/search/Search'
import ConcertList from '../components/concertlist/ConcertList'
import axios from 'axios';
import {API_BASE_URL} from '../constants/index.ts';
import {useQuery} from 'react-query';
import { useState } from 'react';

import Lottie from 'lottie-react';
import { animationLoading } from '../assets/Images/index.js';

export default function SearchPage(){

  const [place,SetPlace] = useState("")
  const [searchWord,SetSearchWord] = useState("")
  const [isSearch,SetIsSearch] = useState(false)

    // API 호출 함수
  const fetchData = async () => {
    console.log(place)
    console.log(searchWord)

    let concertListRequest = {
      currentPage: 1,
      itemCount: 200,
    }

    if(searchWord=="" && place==""){
      concertListRequest = {
        ...concertListRequest,
        orderBy : "now"
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

    console.log(concertListRequest)
  
    const response = await axios.get(`${API_BASE_URL}/concerts`, {params:concertListRequest});
    return response.data;
  };

  const { isLoading, isError, data, error } = useQuery([isSearch], fetchData, {
    refetchOnWindowFocus: false, // react-query는 사용자가 사용하는 윈도우가 다른 곳을 갔다가 다시 화면으로 돌아오면 이 함수를 재실행합니다. 그 재실행 여부 옵션 입니다.
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
        <div className={styles.container}>
            <div
            className={styles.searchBar}>
                <Search 
                place={place} SetPlace={SetPlace} 
                searchWord={searchWord} SetSearchWord={SetSearchWord}
                isSearch={isSearch} SetIsSearch={SetIsSearch}
                />
            </div>

                { isError && <div>Error: {error?.message}</div>}
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
    )
}