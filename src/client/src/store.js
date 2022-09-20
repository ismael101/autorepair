import { configureStore } from "@reduxjs/toolkit";
import authReducer from './features/auth/authSlice'
import workReducer from './features/work/workSlice'
import customerReducer from './features/customer/customerSlice'

export const store = configureStore({
    reducer: {
        auth:authReducer,
        work:workReducer,
        customer:customerReducer
    }
  })