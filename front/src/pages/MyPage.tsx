import styles from "./MyPage.module.css"

export default function MyPage(){
    
    return(
        <div
        className={styles.container}>
            <div 
            className={styles.id}> 
                <h2>
                아이디 : ssafy.ssafy.com
                </h2>
                <img src="" alt="" />
            </div>
        
            <div
            className={styles.point}> 
                <h2>
                TT Money : 10000000000
                </h2>
            </div>
        </div>
    )
}