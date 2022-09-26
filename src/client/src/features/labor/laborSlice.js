import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import { fetchLaborsService, fetchWorkLaborService, updateLaborService, deleteLaborService, createLaborService } from './laborService'

const initialState = {
    labors:[],
    isLoading: false,
    error:null
}

export const fetchLabors = createAsyncThunk(
    'labor/fetch',
    async(_,thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await fetchLaborsService(token)
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const fetchWorkLabors = createAsyncThunk(
    'labor/work/fetch',
    async(id, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await fetchWorkLaborService(token, id)
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const createLabor = createAsyncThunk(
    'labor/create',
    async(labor, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await createLaborService(token, labor)
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const updateLabor = createAsyncThunk(
    'labor/update',
    async(id, labor, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await updateLaborService(token, id, labor)
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const deleteLabor = createAsyncThunk(
    'labor/delete',
    async(id, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await deleteLaborService(token, id)
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const laborSlice = createSlice({
    name:"labor",
    initialState,
    reducers:{
        reset:(state) => initialState
    },
    extraReducers:{
        [fetchLabors.pending]: (state) => {
            state.isLoading = true
        },
        [fetchLabors.fulfilled]: (state, action) => {
            state.isLoading = false
            state.error = null
            state.labors = action.payload.labors
        },
        [fetchLabors.rejected]: (state, action) => {
            state.isLoading = false
            state.error = action.payload
        },
        [fetchWorkLabors.pending]: (state) => {
            state.isLoading = true
        },
        [fetchWorkLabors.fulfilled]: (state, action) => {
            state.isLoading = false
            state.error = null
            state.labors = action.payload.labors
        },
        [fetchWorkLabors.rejected]: (state, action) => {
            state.isLoading = false
            state.error = action.payload
        },
        [createLabor.pending]: (state) => {
            state.isLoading = true
        },
        [createLabor.fulfilled]: (state, action) => {
            state.isLoading = false
            state.error = null
            state.labors.push(action.payload.labor)
        },
        [createLabor.rejected]: (state, action) => {
            state.isLoading = false
            state.error = action.payload
        },
        [updateLabor.pending]: (state) => {
            state.isLoading = true
        },
        [updateLabor.fulfilled]: (state, action) => {
            state.isLoading = false
            state.error = null
            state.labors.forEach(labor => {
                if(labor.id == action.payload.labor.id){
                    labor = action.payload.labor
                }
            })
        },
        [updateLabor.rejected]: (state, action) => {
            state.isLoading - false
            state.error = action.payload  
        },
        [deleteLabor.pending]: (state) => {
            state.isLoading = true
        },
        [deleteLabor.fulfilled]: (state, action) => {
            state.isLoading = false
            state.error = null
            state.labors.filter(labor => labor.id == action.payload.id)
        },
        [deleteLabor.rejected]: (state, action) => {
            state.isLoading = false
            state.error = action.payload
        }
    }
})

export const {reset} = laborSlice.actions
export default laborSlice.reducer