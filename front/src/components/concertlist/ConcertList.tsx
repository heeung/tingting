import styles from './ConcertList.module.css';
import ConcertInfo from '../concertinfo/ConcertInfo';

interface Concert {
  concertSeq: number;
  name: string;
  holdOpenDate: string;
  holdCloseDate: string;
  imageUrl: string;
  concertHallName: string;
  concertHallCity: string;
}

interface ConcertListProps {
  totalPage : number;
  currentPage : number;
  concerts: Concert[]
}

export default function ConcertList(props: ConcertListProps) {
  return (
    <div className={styles.container}>
      {props.props?.concerts ? (
        props.props?.concerts?.map((concert: Concert) => (
          <div className={styles.concertList} key={concert.concertSeq}>
            <ConcertInfo
              concert={concert}
              // 여기에 ConcertInfo 컴포넌트에 전달할 속성을 추가하세요.
              // 예: concertName={concert.name}
            />
          </div>
        ))
      ) : (
        <div>데이터가 없습니다.</div>
      )}
    </div>
  );
}
