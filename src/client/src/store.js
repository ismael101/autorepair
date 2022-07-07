import { configureStore } from "@reduxjs/toolkit";
import jobReducer from './features/jobSlice'
import authReducer from './features/authSlice'


export const store = configureStore({
    reducer: {
      job: jobReducer,
      auth:authReducer
    },
  })