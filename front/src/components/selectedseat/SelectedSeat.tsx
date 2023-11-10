import styles from './SelectedSeat.module.css'

export default function SelectedSeat({seat,price}){

    return(
        <div
        className={styles.container}>
                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 30 30" fill="none">
                        <rect width="30" height="30" fill="#F03B3B"/>
                      </svg>
                      <div>{seat}</div>
                      <div>{price}</div>원
        </div>
    )

} 