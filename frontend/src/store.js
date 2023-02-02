import { combineReducers, configureStore} from "@reduxjs/toolkit";
import { partyReducer } from "./feature/reducer/PartyReducer";


const reducers = combineReducers({
    party : partyReducer,
})

export const store = configureStore({
    reducer : reducers,
})
