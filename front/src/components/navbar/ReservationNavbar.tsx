import styles from './ReservationNavbar.module.css'
import { logo } from '../../assets/Images/index'
import { useNavigate } from 'react-router-dom';
// import { useEffect, useState } from 'react'


export default function Navbar({section,SetSection,holdDate,concertName}){
    
    const navigate = useNavigate()
    const goToOtherPage = (pageName:string) => {
        navigate(`/${pageName}`);
    }

    const onChangeSection = (event) => {
        SetSection(event.target.value)
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
                    <select id="section" value={section} onChange={onChangeSection} 
                    className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                    {/* <option defaultValue="섹션선택">섹션선택</option> */}
                    <option value="A">A</option>
                    <option value="B">B</option>
                    <option value="C">C</option>
                    <option value="D">D</option>
                    <option value="E">E</option>
                    <option value="F">F</option>
                    <option value="G">G</option>
                    <option value="H">H</option>
                    <option value="I">I</option>
                    <option value="J">J</option>
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