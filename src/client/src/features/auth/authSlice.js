import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axios from 'axios'

const token = localStorage.getItem("token")

const initialState = {
    token : token ? token : null,
    isError : false,
    isSuccess : false,
    isLoading: false,
    error:null,
}

export const signup = createAsyncThunk(
    'auth/signup',
    async(user, thunkAPI) => {
        try{
            const response = await axios.post('http://localhost:8080/api/v1/auth/signup', user)
            return response.data
        }catch(error){
            return thunkAPI.rejectWithValue(error)
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
            return thunkAPI.rejectWithValue(error)
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
            state.isError = false
            state.isSuccess = false
            state.isError = false
            state.error = ''
        },
    },
    extraReducers: (builder) => {
        builder
        .addCase(login.pending, (state) => {
            state.isLoading = true
        })
        .addCase(login.fulfilled, (state, action) => {
            state.isLoading = false
            state.isSuccess = true
            state.token = action.payload.token
        })
        .addCase(login.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.error = action.payload
            state.token = null
        })
        .addCase(signup.pending, (state) => {
            state.isLoading = true
        })
        .addCase(signup.fulfilled, (state, action) => {
            state.isLoading = false
            state.isSuccess = true
        })
        .addCase(signup.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.error = action.payload
            state.token = null

        })
        .addCase(logout.fulfilled, (state) => {
            state.token = null
        })
    }
})

export const { reset } = authSlice.actions
export default authSlice.reducer