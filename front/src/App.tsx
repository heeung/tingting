import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Navbar  from './components/navbar/Navbar'
import styles from './App.module.css'
import Login from './pages/Login.tsx'
// import Home from './pages/Home.tsx'
import MyPage from './pages/MyPage.tsx' 
import "./index.css"

function App() {

  return (
    
    <div
      className={styles.container}>
      {/* <Navbar/> */}
      <Login/>
      {/* <Home/> */}
      {/* <MyPage/> */}

    </div>
  )
}

export default App
