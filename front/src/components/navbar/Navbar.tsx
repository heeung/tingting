import styles from './Navbar.module.css'
import { logo } from '../../assets/Images/index'
export default function Navbar(){

    return (
    <div
    className={styles.container}>
        <img
        className={styles.logo}
        src={logo} alt="logo" /> 
        <div
        className={styles.logout}>
            로그인
        </div>
    </div>
    );
}