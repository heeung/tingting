import styles from './ConcertList.module.css'
import ConcertInfo from '../concertinfo/ConcertInfo'



export default function ConcertList(){
    // 티켓 판매 사이트에서는 한 줄의 개수보다 작은 경우에는 더미 이미지를 만들어서 랜더링 해줌 
    const lst = [1,2,3,4,5,6,7,8,9,10]
    return(
        <div
            className={styles.container}>    
            {lst.map((item:number)=>{
                return <div 
                className={styles.concertList}
                key={item}
                >
                    <ConcertInfo
                    />
                </div>

            })}
         
        </div>

    )
}