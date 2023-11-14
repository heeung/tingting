import { kakaoLoginButton, loginBackground, loginLogo, loginLogo2} from '../assets/Images'
import styles from "./Login.module.css"
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

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


  
 
  const REST_API_KEY = '534a17c259cc1b90e15a00a30c7446d6';
  const REDIRECT_URI = 'http://localhost:5173/users/kakao';
  // const REDIRECT_URI = 'http://k9d209.p.ssafy.io:9000/users/kakao';

  const link = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;
  
  const loginHandler = () => {
    window.location.href = link;
  };

  const navigate = useNavigate()
  const goToOtherPage = (pageName:string) => {
      navigate(`/${pageName}`);
  }

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