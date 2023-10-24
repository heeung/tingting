import { kakaoLoginButton, loginBackground, loginLogo, loginLogo2} from "../assets/Images/index"
import styles from "./Login.module.css"
import React, { useState, useEffect } from 'react';

function Login(){
 
  const [isTrue, setIsTrue] = useState(true);

    useEffect(() => {
      const intervalId = setInterval(() => {
        setIsTrue((prevState) => !prevState);
      }, 1000);

    // 컴포넌트가 언마운트되면 타이머를 클리어
    return () => {
      clearInterval(intervalId);
    };
  }, []); // 마운트될 때 한 번만 실행

  const ClickLogin = ()=>{
    alert("영업 안해요")
  }

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
            src={isTrue ? loginLogo : loginLogo2} alt="loginLogo" />
          </div>  
          <div
            className={styles.buttonbox}>
            <img
            onClick={ClickLogin}
            className={styles.button}
            src={kakaoLoginButton} alt="kakaoLoginButton" />  
          </div>
          </div>
        </div>
    )
}

export default Login