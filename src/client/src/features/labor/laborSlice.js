import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import { fetchLaborsService, updateLaborService, deleteLaborService, createLaborService } from './laborService'

const initialState = {
    labors:[],
    loading: false,
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
    async(labor, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await updateLaborService(token, labor)
        }catch(error){
            console.log(error)
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const deleteLabor = createAsyncThunk(
    'labor/delete',
    async(id, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            await deleteLaborService(token, id)
            return id
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
            state.loading = true
        },
        [fetchLabors.fulfilled]: (state, action) => {
            state.loading = false
            state.error = null
            state.labors = action.payload.labors
        },
        [fetchLabors.rejected]: (state, action) => {
            state.loading = false
            state.error = action.payload
        },
        [updateLabor.fulfilled]: (state, action) => {
            state.error = null
            let index = state.labors.findIndex((labor) => labor.id == action.payload.labor.id)
            state.labors[index] = action.payload.labor
        },
        [updateLabor.rejected]: (state, action) => {
            state.error = action.payload  
        },
        [deleteLabor.fulfilled]: (state, action) => {
            state.error = null
            state.labors = state.labors.filter((labor) => labor.id != action.payload)
        },
        [deleteLabor.rejected]: (state, action) => {
            state.error = action.payload
        }
    }
})

export const {reset} = laborSlice.actions
export default laborSlice.reducer