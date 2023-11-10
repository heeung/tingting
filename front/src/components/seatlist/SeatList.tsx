import styles from './SeatList.module.css'
import Seat from '../seat/Seat'

export default function SeatList({seats,setSelectedSeat,selectedSeat}){
    console.log(seats)


    const addSeat = (seat) => {
        if(seat.book == false){

            if(selectedSeat.includes(seat)){
                setSelectedSeat(selectedSeat.filter((s)=>s.concertSeatInfoSeq!==seat.concertSeatInfoSeq))
            }
            else{
                setSelectedSeat([...selectedSeat,seat])
            }
        }
    }
    
    return(
        <div
        className={styles.container}
        >
            {seats && seats.map((seat)=>{
                    return <div 
                    key={seat?.concertSeatInfoSeq}
                    onClick ={()=>{addSeat(seat)}}
                    >
                        {seat.book}
                        <Seat 
                            seat={seat}
                            selectedSeat={selectedSeat}
                        />
                    </div>
                    })}
        </div>
    );
}