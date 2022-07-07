import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const initialState = {
    token:localStorage.getItem('token'),
    authenticated:false,
    loading:false
}


export const loginService = createAsyncThunk(
    'auth/login',
    async(login , thunkAPI) => {
        try{
            const res = await axios.post('http://localhost:8080/api/v1/login', login)
            return res.data
        }catch(err){
            return thunkAPI.rejectWithValue(err)
    }
}
)

const authSlice = createSlice({
    name:'auth',
    initialState, 
    reducers:{
        login:(state, action) => {
            localStorage.setItem('token', action.payload.token)
            state.authenticated = true
        }
    },
    extraReducers:{
        [loginService.pending]: (state) => {
            state.isLoading = true;
        },
        [loginService.fulfilled]: (state) => {
            state.isLoading = false
            state.authenticated = true
        },
        [loginService.rejected]: (state) => {
            state.isLoading = false
        }
    }
})

export const { login } = authSlice.actions

export default authSlice.reducer