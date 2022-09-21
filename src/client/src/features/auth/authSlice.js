import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axios from 'axios'

const token = localStorage.getItem("token")

const initialState = {
    token:token ? token : null,
    isError:false,
    isSuccess:false,
    isLoading:false,
    error:null,
    message:""
}

export const signup = createAsyncThunk(
    'auth/signup',
    async(user, thunkAPI) => {
        try{
            console.log(thunkAPI.getState())
            const response = await axios.post('http://localhost:8080/api/v1/auth/signup', user)
            return response.data
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        }
    }
)

export const login = createAsyncThunk(
    'auth/login',
    async(user, thunkAPI) => {
        try{
            const response = await axios.post('http://localhost:8080/api/v1/auth/login', user)
            await localStorage.setItem('token', response.data.token)
            return response.data
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        }
    }
)

export const logout = createAsyncThunk(
    'auth/logout',
    async() => {
        await localStorage.removeItem('token')
    }
)

export const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        reset: (state) => {
            state.token = null
            state.isLoading = false
            state.isError = false
            state.isSuccess = false
            state.error = null
            state.message = ""
        }
    },
    extraReducers: (builder) => {
        builder
        .addCase(login.pending, (state) => {
            state.isLoading = true
        })
        .addCase(signup.pending, (state) => {
            state.isLoading = true
        })
        .addCase(login.fulfilled, (state, action) => {
            state.isLoading = false
            state.isSuccess = true
            state.token = action.payload.token
        })
        .addCase(signup.fulfilled, (state) => {
            state.isLoading = false
            state.isSuccess = true
        })
        .addCase(logout.fulfilled, (state) => {
            state.token = null
        })
        .addCase(login.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
            state.token = null
        })
        .addCase(signup.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
            state.token = null
        })
    }
})

export const {reset} = authSlice.actions
export default authSlice.reducer