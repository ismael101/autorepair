import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import axios from "axios"

const initialState = {
    labors:[],
    isError:false,
    isSuccess:false,
    isLoading:false,
    message:""
}

export const fetchLabors = createAsyncThunk(
    'labor/fetch',
    async(thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            const response = await axios.get(`http://localhost:8080/api/v1/labors`, config)
            return response.data
        }catch(error){
            thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const createLabor = createAsyncThunk(
    'labor/create',
    async(thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            const response = await axios.post(`http://localhost:8080/api/v1/labors`, config)
            return response.data
        }catch(error){
            thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const updateLabor = createAsyncThunk(
    'labor/update',
    async(id, labor, thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            const response = await axios.put(`http://localhost:8080/api/v1/labors/${id}`, config, labor)
            return response.data
        }catch(error){
            thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const deleteLabor = createAsyncThunk(
    'labor/delete',
    async(id, thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            await axios.delete(`http://localhost:8080/api/v1/labors/${id}`, config)
            return id
        }catch(error){
            thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const laborSlice = createSlice({
    name:"labor",
    initialState,
    reducers:{
        reset:(state) => initialState
    },
    extraReducers:(builder) => {
        builder
        .addCase(fetchLabors.pending, (state) => {
            state.isLoading = true
        })
        .addCase(createLabor.pending, (state) => {
            state.isLoading = true
        })
        .addCase(updateLabor.pending, (state) => {
            state.isLoading = true
        })
        .addCase(deleteLabor.pending, (state) => {
            state.isLoading = true
        })
        .addCase(fetchLabors.fulfilled, (state, action) => {
            state.labor = action.payload.labors
            state.isLoading = false,
            state.isSuccess = true,
            state.isError = false,
            state.message = "labors successfully fetched"
        })
        .addCase(createLabor.fulfilled, (state, action) => {
            state.labor = state.labors.push(action.payload.labor)
            state.isLoading = false,
            state.isSuccess = true,
            state.isError = false,
            state.message = "labor successfully created"
        })
        .addCase(updateLabor.fulfilled, (state) => {
            state.labor = state.labors.forEach(labor => {
                if(labor.id == action.payload.labor.id){
                    labor = action.payload.labor
                }
            })
            state.isLoading = false,
            state.isSuccess = true,
            state.isError = false,
            state.message = "labor successfully updated"
        })
        .addCase(deleteLabor.fulfilled, (state, action) => {
            state.labor = state.labors.filter(labor => labor.id == action.payload)
            state.isLoading = false,
            state.isSuccess = true,
            state.isError = false,
            state.message = "labor successfully deleted"
        })
        .addCase(fetchLabors.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })
        .addCase(createLabor.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })
        .addCase(updateLabor.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })
        .addCase(deleteLabor.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })

    }
})

export const {reset} = laborSlice.actions
export default laborSlice.reducer