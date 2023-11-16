import { atom } from "recoil";

export const userEmailState = atom({
    key: 'userEmailState',
    default: null, // 초기값은 로그인되어 있지 않음을 나타냅니다.
  });
  
  export const userSeqState = atom({
    key: 'userSeqState',
    default: null,
  });