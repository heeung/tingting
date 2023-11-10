import styles from './ReservationNavbar.module.css'
import { logo } from '../../assets/Images/index'
import { useNavigate } from 'react-router-dom';
// import { useEffect, useState } from 'react'


export default function Navbar({holdDate,concertName}){
    
    const navigate = useNavigate()
    const goToOtherPage = (pageName:string) => {
        navigate(`/${pageName}`);
    }

    return (
        <div
        className={styles.container} 
        >
            <div>
                좌석선택
            </div>
            <div>
                {concertName}
            </div>
            <div
                className={styles.selectbar}>
                    <select id="locate" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                    <option defaultValue="섹션선택">섹션선택</option>
                    <option value="V1">V1</option>
                    <option value="V2">V2</option>
                    <option value="V3">V3</option>
                    <option value="V4">V4</option>
                    <option value="N1">N1</option>
                    <option value="N2">N2</option>
                    <option value="N3">N3</option>
                    <option value="N4">N4</option>
                    <option value="N5">N5</option>
                    <option value="N6">N6</option>
                    </select>
                </div>
            <div>
                {holdDate} 
            </div>
            <img
            onClick={() => goToOtherPage('')}
            className={styles.logo}
            src={logo} alt="logo" /> 
        </div>
    );
}