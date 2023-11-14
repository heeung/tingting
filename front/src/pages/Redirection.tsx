import { useEffect } from "react";
import axios from "axios"
import { API_BASE_URL } from "../constants";

const Redirection = (props) => {
    console.log(props)
    useEffect(() => {
        const params= new URL(document.location.toString()).searchParams;
        const code = params.get('code');
        const grantType = "authorization_code";
        const REST_API_KEY = '534a17c259cc1b90e15a00a30c7446d6';
        const REDIRECT_URI = 'http://localhost:5173/users/kakao';  
        // const REDIRECT_URI = 'http://k9d209.p.ssafy.io:9000/users/kakao';    
        console.log(code)
        alert(code)


        // post로 /users/kakao에 code 전달하는 api 추가 아래 쪽에 작성한 api는 쓸모 X
        axios.post(
            `https://kauth.kakao.com/oauth/token?grant_type=${grantType}&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&code=${code}`,
            {},
            { headers: { "Content-type": "application/x-www-form-urlencoded;charset=utf-8" } }
        )
        .then((res: any) => {
            console.log(res);
            const { access_token } = res.data;
            axios.post(
                `https://kapi.kakao.com/v2/user/me`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${access_token}`,
                        "Content-type": "application/x-www-form-urlencoded;charset=utf-8",
                    }
                }
            )
            .then((res: any) => {
                console.log('2번쨰', res);
            })
        })
        .catch((Error: any) => {
            console.log(Error)
        })
    }, [])
    
    return(
        <>
        </>
    )
}
export default Redirection;