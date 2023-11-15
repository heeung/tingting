import styles from './SelectedSeat.module.css'
import Seat from '../seat/Seat'

interface Seat{
    concertSeatInfoSeq:number;
    section:string;
    seat:string;  
    book:boolean;
    grade:string;
    price:number;
  }

interface SelectedSeat{
    seat:Seat
}

export default function SelectedSeat({seat}:SelectedSeat){
    return(
        <div
        className={styles.container}>
            <div>
                <Seat seat={seat} selectedSeat={[seat]} />
            </div>

            <div>{seat.section}</div>
            <div>{seat.grade}</div>
            <div>{seat.price}원</div>
        </div>
    )

} 