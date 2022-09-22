import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { loginService, signupService, logoutService } from './authService'

const token = localStorage.getItem("token")

const initialState = {
    token:token ? token : null,
    isError:false,
    isSuccess:false,
    isLoading:false,
    message:""
}

export const signup = createAsyncThunk(
    'auth/signup',
    async(user, thunkAPI) => {
        try{
            return await signupService(user)
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        }
    }
)

export const login = createAsyncThunk(
    'auth/login',
    async(user, thunkAPI) => {
        try{
            return await loginService(user)
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        }
    }
)

export const logout = createAsyncThunk(
    'auth/logout',
    async() => {
        await logoutService()
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
            state.message = ""
        }
    },
    extraReducers: {
        [login.pending]: (state) => {
            state.isLoading = true
        },
        [login.fulfilled]: (state, action) => {
            state.isLoading = false
            state.isSuccess = true
            state.isError = false
            state.token = action.payload.token
        },
        [login.rejected]: (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        },
        [signup.pending]: (state) => {
            state.isLoading = true
        },
        [signup.fulfilled]: (state, action) => {
            state.isLoading = false
            state.isSuccess = true
            state.isError = false
            state.message = action.payload.message
        },
        [signup.rejected]: (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        },
        [logout.fulfilled]: (state) => {
            state.token = null
        }
    }
})

export const {reset} = authSlice.actions
export default authSlice.reducer