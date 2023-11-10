import styles from './SelectedSeat.module.css'
import Seat from '../seat/Seat'

export default function SelectedSeat({seat}){
    console.log(seat)
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