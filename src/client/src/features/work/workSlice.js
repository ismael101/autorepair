import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { fetchWorksService, fetchWorkService , createWorkService, updateWorkService, deleteWorkService } from './workService'

const initialState = {
    works:[],
    loading:false,
    error:null
}

export const fetchWorks = createAsyncThunk(
    'work/fetch',
    async(_,thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await fetchWorksService(token)
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        }
    }
)

export const fetchWork = createAsyncThunk(
    'work/fetch/id',
    async(id, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await fetchWorkService(token, id)
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        } 
    }
)

export const createWork = createAsyncThunk(
    'work/create',
    async(work, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await createWorkService(token, work)
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        }
    }
)

export const updateWork = createAsyncThunk(
    'work/update',
    async(work, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await updateWorkService(token, work)
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        }
    }
)

export const deleteWork = createAsyncThunk(
    'work/delete',
    async(id, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            await deleteWorkService(token, id)
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
    extraReducers: {
        [fetchWorks.pending]: (state) => {
            state.loading = true
        },
        [fetchWorks.fulfilled]: (state, action) => {
            state.loading = false
            state.error = false
            state.works = action.payload.works
        },
        [fetchWorks.rejected]: (state, action) => {
            state.loading = false
            state.error = action.payload
        },
        [fetchWork.pending]: (state) => {
            state.loading = true
        },
        [fetchWork.fulfilled]: (state, action) => {
            state.loading = false
            state.error = null
            state.works = [action.payload.work]
        },
        [fetchWork.rejected]: (state, action) => {
            state.loading = false
            state.error = action.payload
        },
        [createWork.fulfilled]: (state, action) => {
            state.error = null
            state.works.push(action.payload.work)
        },
        [createWork.rejected]: (state, action) => {
            state.error = action.payload
        },
        [updateWork.fulfilled]: (state, action) => {
            state.error = null
            let index = state.works.findIndex((work) => work.id == action.payload.work.id);
            state.works[index] = action.payload.work
        },
        [updateWork.rejected]: (state, action) => {
            state.error = action.payload
        },
        [deleteWork.fulfilled]: (state, action) => {
            state.error = null
            state.works = state.works.filter((work) => work.id != action.payload)
        },
        [deleteWork.rejected]: (state, action) => {
            state.error = action.payload
        }
    }
})

export const {reset} = workSlice.actions
export default workSlice.reducer