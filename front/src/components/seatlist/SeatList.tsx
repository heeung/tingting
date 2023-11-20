import styles from './SeatList.module.css'
import Seat from '../seat/Seat'

interface Seat{
    concertSeatInfoSeq:number;
    section:string;
    seat:string;  
    book:boolean;
    grade:string;
    price:number;
  }

  interface SeatListProps {
    seats: Seat[];
    setSelectedSeat: React.Dispatch<React.SetStateAction<Seat[]>>;
    selectedSeat: Seat[];
}

export default function SeatList({seats,setSelectedSeat,selectedSeat}:SeatListProps){

    const addSeat = (seat:Seat) => {
        if(seat.book == false){

            if(selectedSeat.includes(seat)){
                setSelectedSeat(selectedSeat.filter((s) => s.concertSeatInfoSeq !== seat.concertSeatInfoSeq))

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
            {seats && seats.map((seat:Seat)=>{
                    return <div 
                    className={styles.seat}
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