import { atom, selector } from "recoil";

export const ConcertItemAtom = atom({
    key:'ConcertItemAtom',
    default:[]
})

export const TotalQuantitySelector = selector({
    key: 'TotalQuantitySelector',
    get: ({get}) => 
    {   const ConcertItem = get(ConcertItemAtom);
        return ConcertItem.length;
    }
})

// export const TotalPriceSelector = selector({
//     key : "TotalPriceSelector",
//     get: ({get})=> {
//         const ConcertItem = get(ConcertItemAtom)
//         return ConcertItem.reduce((누적값, 현재값) => 누적값, 현재값?.price, 0)
//     },
// });