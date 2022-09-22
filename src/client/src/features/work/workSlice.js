import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { fetchService, createService, updateService, deleteService } from './workService'

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
            return await fetchService(token)
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
            return await createService(token, work)
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        }
    }
)

export const updateWork = createAsyncThunk(
    'work/update',
    async(id, work, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await updateService(id, work, token)
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
            await deleteService(id, token)
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
            state.isLoading = true
        },
        [fetchWorks.fulfilled]: (state, action) => {
            state.isLoading = false
            state.isSuccess = true
            state.isError = false
            state.works = action.payload.works
        },
        [fetchWorks.rejected]: (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        },
        [createWork.pending]: (state) => {
            state.isLoading = true
        },
        [createWork.fulfilled]: (state, action) => {
            state.isLoading = false
            state.isSuccess = true
            state.isError = false
            state.works.push(action.payload.work)
        },
        [createWork.rejected]: (state, action) => {
            state.isLoading = false
            state.isSuccess = true
            state.isError = false
            state.message = action.payload.error
        },
        [updateWork.pending]: (state) => {
            state.isLoading = true
        },
        [updateWork.fulfilled]: (state, action) => {
            state.isLoading = false
            state.isSuccess = true
            state.isError = false
            state.works.forEach(work => {
                if(work.id == action.payload.work.id){
                    work = action.payload.work
                }
            })
        },
        [updateWork.rejected]: (state, action) => {
            state.isLoading = false
            state.isSuccess = true
            state.isError = false
            state.message = action.payload.error
        },
        [deleteWork.pending]: (state) => {
            state.isLoading = true
        },
        [deleteWork.fulfilled]: (state, action) => {
            state.isLoading = false
            state.isSuccess = true
            state.isError = false
            state.works.filter(work => work.id == action.payload.id)
        },
        [deleteWork.rejected]: (state, action) => {
            state.isLoading = false
            state.isSuccess = true
            state.isError = false
            state.message = action.payload.error
        }
    }
})

export const {reset} = workSlice.actions
export default workSlice.reducer