import { createAsyncThunk , createSlice} from "@reduxjs/toolkit"
import axios from "axios"

const initialState = {
    parts:[],
    isError:false,
    isSuccess:false,
    isLoading:false,
    message:""
}

export const fetchParts = createAsyncThunk(
    'part/fetch',
    async(thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            const response = await axios.get(`http://localhost:8080/api/v1/parts`, config)
            return response.data
        }catch(error){
            thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const createPart = createAsyncThunk(
    'part/create',
    async(part, thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            const response = await axios.create(`http://localhost:8080/api/v1/parts`, config, part)
            return response.data
        }catch(error){
            thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const updatePart = createAsyncThunk(
    'part/update',
    async(id, part, thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            const response = await axios.put(`http://localhost:8080/api/v1/parts/${id}`, config, part)
            return response.data
        }catch(error){
            thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const deletePart = createAsyncThunk(
    'part/delete',
    async(id, thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            await axios.delete(`http://localhost:8080/api/v1/parts/${id}`, config)
            return id
        }catch(error){
            thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const partSlice = createSlice({
    name:"part",
    initialState,
    reducers:{
        reset:(state) => initialState
    },
    extraReducers:(builder) => {
        builder
        .addCase(fetchParts.pending, (state) => {
            state.isLoading = true
        })
        .addCase(createPart.pending, (state) => {
            state.isLoading = true
        })
        .addCase(updatePart.pending, (state) => {
            state.isLoading = true
        })
        .addCase(deletePart.pending, (state) => {
            state.isLoading = true
        })
        .addCase(fetchParts.fulfilled, (state, action) => {
            state.parts = action.payload.parts
            state.isLoading = false
            state.isSuccess = true
            state.isError = false
            state.message = "part successfully fetched"
        })
        .addCase(createPart.fulfilled, (state, action) => {
            state.parts = state.parts.push(action.payload.part)
            state.isLoading = false
            state.isSuccess = true
            state.isError = false
            state.message = "part successfully created"
        })
        .addCase(updatePart.fulfilled, (state, action) => {
            state.parts = state.parts.forEach(part => {
                if(part.id == action.payload.part.id){
                    part = action.payload.part
                }
            })
            state.isLoading = false
            state.isSuccess = true
            state.isError = false
            state.message = "part successfully updated"
        })
        .addCase(deletePart.fulfilled, (state, action) => {
            state.parts = action.payload.filter(part => part.id == action.payload)
            state.isLoading = false
            state.isSuccess = true
            state.isError = false
            state.message = "part successfully fetched"
        })
        .addCase(fetchParts.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })
        .addCase(createPart.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })
        .addCase(updatePart.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })
        .addCase(deletePart.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })  
    }
})

export const {reset} = partSlice.actions
export default partSlice.reducer