import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"

const initialState = {
    insurances:[],
    isError:false,
    isSuccess:false,
    isLoading:false,
    message:""
}


export const fetchInsurance = createAsyncThunk(
    'insurance/fetch',
    async(thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            const response = await axios.get(`http://localhost:8080/api/v1/insruance`, config)
            return response.data
        }catch(error){
            thunkAPI.rejectValueWith(error.data)
        }
    }
)

export const createInsurance = createAsyncThunk(
    'insurance/create',
    async(insurance, thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            const response = await axios.post(`http://localhost:8080/api/v1/insruance`, config, insurance)
            return response.data
        }catch(error){
            thunkAPI.rejectValueWith(error.data)
        }
    }
)

export const updateInsurance = createAsyncThunk(
    'insurance/update',
    async(insurance, thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            const response = await axios.put(`http://localhost:8080/api/v1/insruance`, config, insurance)
            return response.data
        }catch(error){
            thunkAPI.rejectValueWith(error.data)
        }
    }
)

export const deleteInsurance = createAsyncThunk(
    'insurance/delete',
    async(thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            await axios.delete(`http://localhost:8080/api/v1/insruance`, config)
            return id
        }catch(error){
            thunkAPI.rejectValueWith(error.data)
        }
    }
)

export const insuranceSlice = createSlice({
    name:"insurances",
    initialState,
    reducers:{
        reset:(state) => initialState
    },
    extraReducers:(builder) => {
        builder
        .addCase(fetchInsurance.pending, (state) => {
            state.isLoading = false
        })
        .addCase(createInsurance.pending, (state) => {
            state.isLoading = false
        })
        .addCase(updateInsurance.pending, (state) => {
            state.isLoading = false
        })
        .addCase(deleteInsurance.pending, (state) => {
            state.isLoading = false
        })
        .addCase(fetchInsurance.fulfilled, (state, action) => {
            state.insurances = action.payload.insurances
            state.isLoading = false
            state.isSuccess = true
            state.isError = false
            message = "insurances successfully fetched"
        })
        .addCase(createInsurance.fulfilled, (state, action) => {
            state.insurances.push(action.payload.insurance)
            state.isLoading = false
            state.isSuccess = true
            state.isError = false
            message = "insurances successfully created"
        })
        .addCase(updateInsurance.fulfilled, (state, action) => {
            state.insurances = state.insurances.forEach(insurance => {
                if(insurance.id == action.payload.insurance.id){
                    insurance = action.payload.insurance
                }
            })
            state.isLoading = false
            state.isSuccess = true
            state.isError = false
            message = "insurances successfully updated"
        })
        .addCase(deleteInsurance.fulfilled, (state, action) => {
            state.insurances = state.insurances.filter(insurance => insurance.id == action.payload)
            state.isLoading = false
            state.isSuccess = true
            state.isError = false
            message = "insurances successfully deleted"
        })
    }
})