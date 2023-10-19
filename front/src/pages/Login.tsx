import { kakaoLoginButton, loginBackground, loginLogo} from "../assets/Images/index"
import styles from "./Login.module.css"
function login(){

    return(
        <div
         className={styles.container}
            >
          <img
          className={styles.img}
          src={loginBackground} alt="loginBackground" />
          <div
          className={styles.loginbox}
          >
          <div
            className={styles.logobox}>
            <img
            className={styles.logo}
            src={loginLogo} alt="loginLogo" />
          </div>  
          <div
            className={styles.buttonbox}>
            <img
            className={styles.button}
            src={kakaoLoginButton} alt="kakaoLoginButton" />  
          </div>
          </div>
        </div>
    )
}

export default login