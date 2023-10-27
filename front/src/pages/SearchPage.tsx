import styles from './SearchPage.module.css'
import Search from '../components/search/Search'
import ConcertList from '../components/concertlist/ConcertList'

export default function SearchPage(){
    return(
        <div className={styles.container}>
            <div
            className={styles.searchBar}>
                <Search/>
            </div>
            <div 
            className={styles.concertList}>
                <ConcertList/>
            </div>
        </div>
    )
}