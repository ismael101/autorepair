import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axios from 'axios'

const initialState = {
    works:[],
    isError:false,
    isSuccess:false,
    isLoading:false,
    message:null,
}

export const fetchWorks = createAsyncThunk(
    'work/fetch',
    async(_,thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            const config = {
                headers:{
                    Authorization: `Bearer ${token}`
                }
            }
            const repsonse = await axios.get('http://localhost:8080/api/v1/work', config)
            return repsonse.data
        }catch(error){
            return thunkAPI.rejectWithValue(error.response)
        }
    }
)

export const createWork = createAsyncThunk(
    'work/create',
    async(work, thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization: `Bearer ${thunkAPI.getState().auth.token}`
                }
            }
            const response = await axios.post(`http://localhost:8080/api/v1/work`, work, config)
            return response.data
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        }
    }
)

export const updateWork = createAsyncThunk(
    'work/update',
    async(id, work, thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization: `Bearer ${thunkAPI.getState().auth.token}`
                }
            }
            const response = await axios.put(`http://localhost:8080/api/v1/work/${id}`, work, config)
            return response.data
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        }
    }
)

export const deleteWork = createAsyncThunk(
    'work/delete',
    async(id, thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization: `Bearer ${thunkAPI.getState().auth.token}`
                }
            }
            await axios.delete(`http://localhost:8080/api/v1/work/${id}`, config)
            return id
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        }
    }
)


export const workSlice = createSlice({
    name:"work",
    initialState,
    reducers:{
        reset: (state) => initialState,
    },
    extraReducers: (builder) => {
        builder
        .addCase(fetchWorks.pending, (state) => {
            state.isLoading = true
        })
        .addCase(createWork.pending, (state) => {
            state.isLoading = true
        })
        .addCase(updateWork.pending, (state) => {
            state.isLoading = true
        })
        .addCase(deleteWork.pending, (state) => {
            state.isLoading = true
        })
        .addCase(fetchWorks.fulfilled, (state, action) => {
            state.works = action.payload.works
            state.isLoading = false
            state.isSuccess = true
            state.message = "work orders succesfully fetched"
        }) 
        .addCase(createWork.fulfilled, (state, action) => {
            state.works = state.works.push(action.payload.work)
            state.isLoading = false
            state.isSuccess = true
            state.message = "work order successfully created"
        })
        .addCase(updateWork.fulfilled, (state, action) => {
            state.works = state.works.forEach(work => {
                if(work.id == action.payload.work.id){
                    work = action.payload.work
                }
            })
            state.isLoading = false
            state.isSuccess = true
            state.message = "work order successfully updated"
        })
        .addCase(deleteWork.fulfilled, (state, action) => {
            state.works = state.works.filter(work => work.id == action.payload)
            state.isLoading = false
            state.isSuccess = true
            state.message = "work order successfully deleted"
        })
        .addCase(fetchWorks.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload
        })
        .addCase(createWork.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })
        .addCase(updateWork.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })
        .addCase(deleteWork.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })
    }
})

export const {reset} = workSlice.actions
export default workSlice.reducer