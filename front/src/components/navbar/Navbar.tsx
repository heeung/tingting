import styles from './Navbar.module.css'
import { logo } from '../../assets/Images/index'
import { useNavigate, useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react'
import { useRecoilState } from 'recoil';
import { userEmailState, userSeqState } from "../../recoil/UserAtom.tsx";


export default function Navbar(){

    const [userEmail,setUserEmail] = useRecoilState(userEmailState);
    const [userSeq,setUserSeq] = useRecoilState(userSeqState);
    
    const location = useLocation();
    const [isLoginPage, setIsLoginPage] = useState(location.pathname === '/login');
    const [isReservationPage, setIsReservationPage] = useState(location.pathname === '/concert/reservation');
  

    useEffect(()=>{
      setIsLoginPage( window.location.pathname=== '/login')
      setIsReservationPage( window.location.pathname=== '/concert/reservation')
    },[location.pathname])

    const navigate = useNavigate()
    const goToOtherPage = (pageName:string) => {
        navigate(`/${pageName}`);
    }
    const logout = () => {
        setUserEmail(null)
        setUserSeq(null)
    }

    if (isLoginPage || isReservationPage) {
        return null; // 로그인 페이지와 공연예약페이지에서 네비게이션 바를 숨김
      }
  

    return (
    <div
    className={styles.container} 
    >

        <img
        onClick={() => goToOtherPage('')}
        className={styles.logo}
        src={logo} alt="logo" /> 
        
        <div
        onClick={() => goToOtherPage('search')}
        className={styles.logout}>
            Search
        </div>
        {
            userSeq &&
            <div
            onClick={() => goToOtherPage('mypage')}
            className={styles.mypage}>
                MyPage
            </div>
        }

        {
            userSeq &&
            <div>
                {userEmail}
            </div>
        }

        {userSeq == null
            ? 
            <div
            onClick={() => goToOtherPage('login')}
            className={styles.login}>
                로그인
            </div>
            :
            <div
            onClick={() => logout()}
            className={styles.logout}>
                로그아웃
            </div>
        }
    </div>
    );
}