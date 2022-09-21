import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import axios from "axios"

const initialState = {
    customers:[],
    isError:false,
    isSuccess:false,
    isLoading:false,
    message:""
}

export const fetchCustomers = createAsyncThunk(
    'customer/fetch',
    async(thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            const response = await axios.get(`http://localhost:8080/api/v1/customers`, config)
            return response.data
        }catch(error){
            thunkAPI.rejectWithValue(thunkAPI.error.data)
        }
    }
)

export const createCustomer = createAsyncThunk(
    'customer/create',
    async(customer, thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            const response = await axios.post(`http://localhost:8080/api/v1/customers`, config, customer)
            return response.data
        }catch(error){
            thunkAPI.rejectWithValue(thunkAPI.error.data)
        }
    }
)

export const updateCustomer = createAsyncThunk(
    'customer/update',
    async(id, customer, thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            const response = await axios.put(`http://localhost:8080/api/v1/customers/${id}`, config, customer)
            return response.data
        }catch(error){
            thunkAPI.rejectWithValue(thunkAPI.error.data)
        }
    }
)

export const deleteCustomer = createAsyncThunk(
    'customer/delete',
    async(id, thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            await axios.delete(`http://localhost:8080/api/v1/customers/${id}`, config)
            return id
        }catch(error){
            thunkAPI.rejectWithValue(thunkAPI.error.data)
        }
    }
)


export const customerSlice = createSlice({
    name:"customer",
    initialState,
    reducers:{
        reset:(state) => initialState
    },
    extraReducers: (builder) => {
        builder
        .addCase(fetchCustomers.pending, (state) => {
            state.isLoading = true
        })
        .addCase(createCustomer.pending, (state) => {
            state.isLoading = true
        })
        .addCase(updateCustomer.pending, (state) => {
            state.isLoading = true
        })
        .addCase(deleteCustomer.pending, (state) => {
            state.isLoading = true
        })
        .addCase(fetchCustomers.fulfilled, (state, action) => {
            state.customers = action.payload.customers
            state.isLoading = false
            state.isSuccess = true
            state.message = "customers successfully fetched"
        })
        .addCase(createCustomer.fulfilled, (state, action) => {
            state.customers = state.customers.push(action.payload)
            state.isLoading = false
            state.isSuccess = true
            state.message = "customer successfully created"
        })
        .addCase(updateCustomer.fulfilled, (state, action) => {
            state.customers = state.customers.forEach(customer => {
                if(customer.id == action.payload.customer.id){
                    customer = action.payload
                }
            })
            state.isLoading = false
            state.isSuccess = true
            state.message = "customer successfully updated"
        })
        .addCase(deleteCustomer.fulfilled, (state, id) => {
            state.customers = state.customers.filter(customer => customer.id == id)
            state.isLoading = false
            state.isSuccess = true
            state.message = "customer successfully deleted"
        })
        .addCase(fetchCustomers.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })
        .addCase(createCustomer.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })
        .addCase(updateCustomer.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })
        .addCase(deleteCustomer.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })
    }
})

export const {reset} = customerSlice.actions
export default customerSlice.reducer


