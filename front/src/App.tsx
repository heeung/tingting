// import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import styles from './App.module.css'
// import Navbar  from './components/navbar/Navbar'
import Login from './pages/Login.tsx'
// import Home from './pages/Home.tsx'
// import MyPage from './pages/MyPage.tsx' 
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
      <RecoilRoot>
        <Login/>
      </RecoilRoot>
      {/* <Navbar/> */}
      {/* <Home/> */}
      {/* <MyPage/> */}

    </div>
  )
}

export default App
