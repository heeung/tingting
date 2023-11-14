import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import styles from './App.module.css'
import Navbar  from './components/navbar/Navbar'
import Login from './pages/Login.tsx'
import Home from './pages/Home.tsx'
import MyPage from './pages/MyPage.tsx' 
import SearchPage from './pages/SearchPage.tsx';
import ConcertDetail from './pages/ConcertDetail.tsx'
import ConcertReservation from './pages/ConcertReservation.tsx';
import Redirection from './pages/Redirection.tsx';
import "./index.css"
import {
  RecoilRoot,
  // atom,
  // selector,
  // useRecoilState,
  // useRecoilValue,
} from 'recoil';

function App() {

  return (
  <div
    className={styles.container}>
      <Router>
        <RecoilRoot>
          <div className={styles.header}>
            <Navbar />
          </div>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/mypage" element={<MyPage />} />
            <Route path="/search" element={<SearchPage />} />
            <Route path="/concert/detail/:concertseq" element={<ConcertDetail/>} />
            <Route path="/concert/reservation" element={<ConcertReservation/>}></Route>
            <Route path='/users/kakao' element={<Redirection />} ></Route>
          </Routes>  
        </RecoilRoot>
      </Router>
  </div>
  )
}

export default App
