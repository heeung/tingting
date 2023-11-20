import { kakaoLoginButton, loginBackground, loginLogo, loginLogo2} from '../assets/Images'
import styles from "./Login.module.css"
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {REST_API_KEY,REDIRECT_URI} from '../constants'

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

  const link = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;
  
  const loginHandler = () => {
    window.location.href = link;
  };

  const navigate = useNavigate()
  const goToOtherPage = (pageName:string) => {
      navigate(`/${pageName}`);
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
            onClick={()=>goToOtherPage('')}
            className={styles.logo}
            src={isTrue ? loginLogo : loginLogo2} alt="loginLogo" />
          </div>  
          <div
            className={styles.buttonbox}>
            <img
            onClick={loginHandler}
            className={styles.button}
            src={kakaoLoginButton} alt="kakaoLoginButton" />  
          </div>
          </div>
        </div>
    )
}

export default Login