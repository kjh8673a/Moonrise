import { combineReducers, configureStore} from "@reduxjs/toolkit";
import movieReducer from "./feature/reducer/MovieReducer";
import partyReducer from "./feature/reducer/PartyReducer";


const reducers = combineReducers({
    party : partyReducer,
    movie : movieReducer,
})

export const store = configureStore({
    reducer : reducers,
})
