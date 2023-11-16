import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist({
  key: "localStorage",
  storage: localStorage,
});

export const userEmailState = atom({
  key: 'userEmailState',
  default: null,
  effects_UNSTABLE: [persistAtom], 
});

export const userSeqState = atom({
  key: 'userSeqState',
  default: null,
  effects_UNSTABLE: [persistAtom], 
});
