import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const token = localStorage.getItem('token')

const initialState = {
    token: token ? token : null,
    error: null,
    loading: false
}


export const loginService = createAsyncThunk(
    'auth/login',
    async(credentials , thunkAPI) => {
        try{
            const res = await axios.post('http://localhost:8080/api/v1/auth/login', credentials)
            localStorage.setItem('token', res.data.token)
            return res.data
        }catch(err){
            return thunkAPI.rejectWithValue(err.response)
    }
}
)

export const logoutService = createAsyncThunk(
    'auth/logout',
    () => {
        localStorage.removeItem('token')
    }
)


const authSlice = createSlice({
    name:'auth',
    initialState, 
    reducers:{},
    extraReducers:{
        [loginService.pending]: (state) => {
            state.isLoading = true;
        },
        [loginService.fulfilled]: (state, action) => {
            state.loading = false
            state.error = null
            state.token = action.payload.token
        },
        [loginService.rejected]: (state, action) => {
            state.loading = false
            state.error = action.payload.data
        },
        [logoutService.fulfilled]: (state) => {
            state.loading = false
            state.error = null
            state.token = null
        }
    }
})

export default authSlice.reducer