import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist({
  key: "localStorage",
  storage: localStorage,
});

export const userEmailState = atom({
  key: 'userEmailState',
  default: "knuee2014@naver.com",
  effects_UNSTABLE: [persistAtom], 
});

export const userSeqState = atom({
  key: 'userSeqState',
  default: 675330,
  effects_UNSTABLE: [persistAtom], 
});
