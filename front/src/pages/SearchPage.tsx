import styles from './SearchPage.module.css'
import Search from '../components/search/Search'
import ConcertList from '../components/concertlist/ConcertList'
import axios from 'axios';
import {API_BASE_URL} from '../constants/index.ts';
import {useQuery} from 'react-query';
import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import Lottie from 'lottie-react';
import { animationLoading } from '../assets/Images/index.js';

type ConcertListRequest = {
  currentPage: number;
  itemCount: number;
  orderBy?: string;
  searchWord?:string;
  place?:string;
};


export default function SearchPage(){

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
  const fetchData = async () => {

    let concertListRequest:ConcertListRequest = {
      currentPage: 1,
      itemCount: 200,
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

  const { isLoading, data } = useQuery([isSearch], fetchData, {
    refetchOnWindowFocus: false,
    // onSuccess: data => {
    //   console.log(data);
    // },
    onError: e => {
      console.log(e);
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

                {
                  !isLoading && data?.concerts!==undefined && <ConcertList props={data} searchWord={searchWord}/>
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