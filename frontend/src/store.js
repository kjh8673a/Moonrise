import { combineReducers, configureStore } from "@reduxjs/toolkit";
import movieReducer from "./feature/reducer/MovieReducer";
import partyReducer from "./feature/reducer/PartyReducer";
import storage from "redux-persist/lib/storage/session";
import { persistReducer } from "redux-persist";

const persistConfig = {
  key: "root",
  storage,
};

const reducers = combineReducers({
  party: partyReducer,
  movie: movieReducer,
});

const persistedReducer = persistReducer(persistConfig, reducers);

export const store = configureStore({
  reducer: persistedReducer,
});
