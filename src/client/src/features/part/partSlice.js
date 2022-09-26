import { createAsyncThunk , createSlice} from "@reduxjs/toolkit"
import { fetchPartsService, createPartService, updatePartService, deletePartService } from './partService'

const initialState = {
    parts:[],
    isLoading:false,
    error:null
}

export const fetchParts = createAsyncThunk(
    'part/fetch',
    async(_,thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await fetchPartsService(token)
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const fetchWorkParts = createAsyncThunk(
    'part/work/fetch',
    async(id, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await fetchWorkParts(token, id)
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const createPart = createAsyncThunk(
    'part/create',
    async(part, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await createPartService(token, part)
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)


export const updatePart = createAsyncThunk(
    'part/update',
    async(id, part, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await updatePartService(toke, id, part)
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const deletePart = createAsyncThunk(
    'part/delete',
    async(id, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            await deletePartService(token, id)
            return id
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const part = createSlice({
    name:"part",
    initialState,
    reducers:{
        reset:(state) => initialState
    },
    extraReducers:{
        [fetchParts.pending]:(state) => {
            state.isLoading = true
        },
        [fetchParts.fulfilled]:(state, action) => {
            state.isLoading = false
            state.error = null
            state.parts = action.payload.parts
        },
        [fetchParts.rejected]:(state, action) => {
            state.isLoading = false
            state.error = action.payload
        },
        [fetchWorkParts.pending]: (state) => {
            state.isLoading = true
        },
        [fetchWorkParts.fulfilled]: (state, action) => {
            state.isLoading = false
            state.error = null
            state.parts = action.payload.parts   
        },
        [fetchWorkParts.rejected]: (state, action) => {
            state.isLoading = false
            state.error = action.payload
        },
        [updatePart.pending]:(state) => {
            state.isLoading = true
        },
        [updatePart.fulfilled]:(state, action) => {
            state.isLoading = false
            state.error = null
            state.parts.forEach(part => {
                if(part.id == action.payload.part.id){
                    part = action.payload.part
                }
            })
        },
        [updatePart.rejected]:(state, action) => {
            state.isLoading = false
            state.error = action.payload
        },
        [deletePart.pending]:(state) => {
            state.isLoading = true
        },
        [deletePart.fulfilled]:(state, action) => {
            state.isLoading = false
            state.error = null
            state.parts.filter(part => part.id == action.payload.id)
        },
        [deletePart.rejected]:(state, action) => {
            state.isLoading = false
            state.error = action.payload
        }         
    }
})

export const {reset} = partSlice.actions
export default partSlice.reducer