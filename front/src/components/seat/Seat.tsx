import styles from './Seat.module.css'

interface Seat{
    concertSeatInfoSeq:number;
    section:string;
    seat:string;  
    book:boolean;
    grade:string;
    price:number;
  }

interface SeatProps{
    seat:Seat;
    selectedSeat:Seat[];

}

export default function Seat({seat,selectedSeat}:SeatProps){

    const isSelectedSeat = () => {
        if(selectedSeat.includes(seat)){
            return true
        }
        return false
    }

    const isVip = () => {
        if(seat.grade=="VIP"){
            return true
        }
        return false
    }
    return(
        <div className={styles.container}>
            <div className={styles.seat}>
                    <div className={styles["seat-number"]}>
                        {seat.seat}
                    </div>
                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 30 30" fill="none">
                    
                    { seat.book
                        ?
                        <rect width="30" height="30" fill="#D9D9D9"/>
                        :
                        isSelectedSeat()
                        ?
                        <rect width="30" height="30" fill="#F03B3B"/>
                        :
                        isVip()
                        ?
                        <rect width="30" height="30" fill="#EB7BFD"/>
                        :
                        <rect width="30" height="30" fill="#90BCE5"/>
                    }
                </svg>
            </div>
        </div>
    );
}