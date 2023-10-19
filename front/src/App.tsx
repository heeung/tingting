import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import styles from './App.module.css'
import Login from './pages/Login.tsx'
import Home from './pages/Home.tsx'

function App() {

  return (
    <div
      className={styles.container}>
      {/* <Login/> */}
      <Home/>
    </div>
  )
}

export default App
