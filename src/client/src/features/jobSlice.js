import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import axios from "axios"


const initialState = {
    jobs:[],
    error:null,
    loading:false
}

export const fetchJobService = createAsyncThunk(
    'job/fetch',
    async(token, thunkAPI) => {
        try{
            const res = await axios.get('http://localhost:8080/api/v1/job/', {
                headers:{
                    Authorization: `Bearer ${token}`
                }
            })
            return res.data
        }catch(err){
            return thunkAPI.rejectWithValue(err.response)
        }
    }
)


export const createJobService = createAsyncThunk(
    'job/create',
    async(body, thunkAPI) => {
        try{
            const res = await axios.post('http://localhost:8080/api/v1/job/', body.job, {
                headers:{
                    Authorization: `Bearer ${body.token}`
                }
            })
            return res.data
        }catch(err){
            return thunkAPI.rejectWithValue(err.response)
        }
    }
)

const jobSlice = createSlice({
    name:"job",
    initialState,
    reducers:{},
    extraReducers:{
        [fetchJobService.pending]: (state) => {
            state.loading = true;
        },
        [fetchJobService.fulfilled]: (state, action) => {
            state.loading = false
            state.error = null
            state.jobs = action.payload
        },
        [fetchJobService.rejected]: (state, action) => {
            state.loading = false
            state.error = action.payload.data
        },
        [createJobService.pending]: (state) => {
            state.loading = true;
        },
        [createJobService.fulfilled]: (state, action) => {
            state.loading = false
            state.error = null
            state.jobs.push(action.payload)
        },
        [createJobService.rejected]: (state, action) => {
            state.loading = false
            state.error = action.payload.data
        },
    }
})

export default jobSlice.reducer