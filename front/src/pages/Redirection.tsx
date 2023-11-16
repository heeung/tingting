import { useEffect } from "react";
import axios from "axios"
import { API_BASE_URL } from "../constants";
import { useSetRecoilState } from 'recoil';
import { userEmailState, userSeqState } from "../recoil/UserAtom";
import { useNavigate } from 'react-router-dom'


const Redirection = () => {

    const setUserEmail = useSetRecoilState(userEmailState);
    const setUserSeq = useSetRecoilState(userSeqState);
    const params= new URL(document.location.toString()).searchParams;
    const code = params.get('code');

    const navigate = useNavigate()
    const goToOtherPage = (pageName:string) => {
      navigate(`/${pageName}`);
    }

    const login = async () => {
        await axios.post(`${API_BASE_URL}/users/kakao`, { code })
            .then((res) => {
                console.log(res);
                setUserEmail(res.data.userEmail)
                setUserSeq(res.data.userSeq)
                goToOtherPage('')
                
            })
            .catch((error) => {
                console.error(error);
            });
    };


    useEffect(() => {
        login()
    }, [])
    
    return(
        <>
        </>
    )
}
export default Redirection;