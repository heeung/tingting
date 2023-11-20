import styles from './ConcertList.module.css';
import ConcertInfo from '../concertinfo/ConcertInfo';
import {deactivatedLogo} from '../../assets/Images/index'

interface Concert {
  concertSeq: number;
  name: string;
  holdOpenDate: string;
  holdCloseDate: string;
  imageUrl: string;
  concertHallName: string;
  concertHallCity: string;
}

interface Props {
  searchWord : string;
  props : {
    concerts : Concert[];
    currentPage : number;
    totalPage : number;
  }
  isLiked? : boolean
}

export default function ConcertList(props:Props) {
  const concerts = props?.props?.concerts 
  const searchWord = props.searchWord
  const isLiked = props.isLiked
  
  return (
    <div className={styles.container}>
      {concerts && (concerts.length > 0) 
      ? 
      (
        concerts?.map((concert: Concert, index) => (
          <div className={styles.concertList} key={`${concert.concertSeq}-${index}`}>
            <ConcertInfo
              concert={concert}
            />
          </div>
        ))
      ) : 
      ( 
        <div
        className={styles['comment-background']}
        >
          <div>
          <img 
          className={styles.img}
          src={deactivatedLogo} alt="deactivatedLogo" />
          </div>
          {
            isLiked 
            ?
            <div
            className={styles.comment}
            >찜한 공연이 없습니다</div>            
            :
            <div
            className={styles.comment}
            >"{searchWord}"로 검색한 결과가 없습니다.</div>
          }
        </div>
      )}
    </div>
  );
}
