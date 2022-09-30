import { createAsyncThunk , createSlice} from "@reduxjs/toolkit"
import { fetchPartsService, createPartService, updatePartService, deletePartService } from './partService'

const initialState = {
    parts:[],
    loading:false,
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
    async(part, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await updatePartService(token, part)
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

export const partSlice = createSlice({
    name:"part",
    initialState,
    reducers:{
        reset:(state) => initialState
    },
    extraReducers:{
        [fetchParts.pending]:(state) => {
            state.loading = true
        },
        [fetchParts.fulfilled]:(state, action) => {
            state.loading = false
            state.error = null
            state.parts = action.payload.parts
        },
        [fetchParts.rejected]:(state, action) => {
            state.loading = false
            state.error = action.payload
        },
        [updatePart.fulfilled]:(state, action) => {
            state.error = null
            let index = state.parts.findIndex((part) => part.id == action.payload.part.id);
            state.parts[index] = action.payload.part
        },
        [updatePart.rejected]:(state, action) => {
            state.error = action.payload
        },
        [deletePart.fulfilled]:(state, action) => {
            state.error = null
            state.parts = state.parts.filter((part) => part.id != action.payload)
        },
        [deletePart.rejected]:(state, action) => {
            state.error = action.payload
        }         
    }
})

export const {reset} = partSlice.actions
export default partSlice.reducer