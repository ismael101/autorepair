import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { loginService, signupService, logoutService } from './authService'

const token = localStorage.getItem("token")

const initialState = {
    token:token ? token : null,
    loading:false,
    error:null,
    message:null
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
            state.loading = false
            state.error = null
            state.success = false
            state.message = null
        }
    },
    extraReducers: {
        [login.pending]: (state) => {
            state.loading = true
        },
        [login.fulfilled]: (state, action) => {
            state.loading = false
            state.error = null
            state.token = action.payload.token
        },
        [login.rejected]: (state, action) => {
            state.loading = false
            state.error = action.payload
        },
        [signup.pending]: (state) => {
            state.loading = true
        },
        [signup.fulfilled]: (state, action) => {
            state.loading = false
            state.error = null
            state.message = action.payload.message
        },
        [signup.rejected]: (state, action) => {
            state.loading = false
            state.error = action.payload
            state.message = action.payload.error
        },
        [logout.fulfilled]: (state) => {
            state.token = null
        }
    }
})

export const {reset} = authSlice.actions
export default authSlice.reducer