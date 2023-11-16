import { useState, useEffect, useRef, useCallback  } from 'react';
import styles from './Home.module.css'
import Search from '../components/search/Search.js'
import ConcertList from '../components/concertlist/ConcertList.js'
import Carousel from '../components/carousel/MyCarousel.js';

import Lottie from 'lottie-react';
import { animationLoading } from '../assets/Images/index.js';
import { useNavigate } from 'react-router-dom'


import { useRecoilValue } from 'recoil';
import { userEmailState, userSeqState } from "../recoil/UserAtom.tsx";
import {
  useInfiniteQuery, 
  } from 'react-query';

import axios from 'axios';
import {API_BASE_URL} from '../constants/index.ts';

function Home(){

  const userEmail = useRecoilValue(userEmailState);
  const userSeq = useRecoilValue(userSeqState);

  console.log('userEmail:',userEmail)
  console.log('userSeq:',userSeq)

  const navigate = useNavigate()

  const [maxPage,SetMaxPage] = useState(1)
  const [place,SetPlace] = useState<string>("")
  const [searchWord,SetSearchWord] = useState<string>("")
  const [isSearch,SetIsSearch] = useState<boolean>(false)

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

    // API 호출 함수
  const fetchData = async (page=1) => {
  
  const concertListRequest = {
    currentPage: page,
    itemCount: 60,
    orderBy : category
  }

  const response = await axios.get(`${API_BASE_URL}/concerts`, {params:concertListRequest});
  return response.data;
};


const observerElem = useRef(null);

  const { data, isLoading, hasNextPage, fetchNextPage, isFetchingNextPage } =
    useInfiniteQuery(
      [queryKey],
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

    // const { isLoading, data } = useQuery([queryKey], ()=>fetchData({pageParam:1}), {
    //     refetchOnWindowFocus: false, // react-query는 사용자가 사용하는 윈도우가 다른 곳을 갔다가 다시 화면으로 돌아오면 이 함수를 재실행합니다. 그 재실행 여부 옵션 입니다.
    //     retry: 1, // 실패시 재호출 몇번 할지
    //     // onSuccess: data => {
    //     //   console.log(data);
    //     // },
    //     onError: e => {
    //       console.log(e);
    //     }
    //   });

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
                  !isLoading && <ConcertList props={allPagesContent} searchWord=""/>
                }
                { isLoading  && 
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
    );
   

}

export default Home