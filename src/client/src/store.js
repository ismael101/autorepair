import { configureStore } from "@reduxjs/toolkit";
import authReducer from './features/auth/authSlice'
import workReducer from './features/work/workSlice'
import customerReducer from './features/customer/customerSlice'
import insuranceReducer from './features/insurance/insuranceSlice'
import laborSlice from './features/labor/laborSlice'
import partSlice from './features/part/partSlice'
import vehicleSlice from './features/vehicle/vehicleSlice'

export const store = configureStore({
    reducer: {
        auth:authReducer,
        work:workReducer,
        customer:customerReducer,
        insurance:insuranceReducer,
        labor:laborSlice,
        part:partSlice,
        vehicle:vehicleSlice
    }
  }) 