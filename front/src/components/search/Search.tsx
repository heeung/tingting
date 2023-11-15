import styles from './Search.module.css'
// import Calendar from '../calendar/Calendar.js';


interface SearchProps {
    place:string;
    SetPlace: (key: string) => void;
    searchWord:string;
    SetSearchWord:(key: string) => void;
    isSearch:boolean;
    SetIsSearch:(key: boolean) => void;
  }



export default function Search({place,SetPlace,searchWord,SetSearchWord,isSearch,SetIsSearch}:SearchProps){

    const onChangePlace:React.ChangeEventHandler<HTMLSelectElement> = (event) => {
        console.log(event.target.value)
        SetPlace(event.target.value)
    }

    const onChangeSearchWord:React.ChangeEventHandler<HTMLInputElement> = (event) => {
        console.log(event.target.value)
        SetSearchWord(event.target.value)
    }

    const Search:React.FormEventHandler<HTMLFormElement>  = (event) => {
        event.preventDefault()
        SetIsSearch(!isSearch)
    }

    return( 
        // col,row로 다시 만들기
        <div 
        className={styles.container}>

            <div 
            className={styles.select}>
                <div
                className={styles.selectbar}>
                    {/* <Calendar/>
                    <Calendar/> */}
                    {/* <select id="date" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                    <option defaultValue=""><Calendar/></option>
                    </select> */}
                </div>
                <div
                className={styles.selectbar}>
                    <select value={place} onChange={onChangePlace} id="place" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                    <option defaultValue="">장소</option>
                    <option value="서울">서울</option>
                    <option value="부산">부산</option>
                    <option value="대전">대전</option>
                    <option value="대구">대구</option>
                    <option value="광주">광주</option>
                    <option value="울산">울산</option>
                    <option value="인천">인천</option>
                    <option value="경북">경북</option>
                    </select>
                </div>
            </div>
            
            <form className={styles.searchbar} onSubmit={Search}>   
                <div className="relative">
                    <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                        <svg className="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                            <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                        </svg>
                    </div>
                    <input value={searchWord} onChange={onChangeSearchWord} type="search" id="default-search" className="block w-full p-4 pl-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Search Mockups, Logos..." required/>
                    <button type="submit" className="text-white absolute right-2.5 bottom-2.5 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Search</button>
                </div>
            </form>

            
        </div>
    );

}