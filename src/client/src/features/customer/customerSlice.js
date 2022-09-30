import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import { fetchCustomersService, updateCustomerService, deleteCustomerService, createCustomerService } from './customerService'

const initialState = {
    customers:[],
    loading:false,
    error:null
}

export const fetchCustomers = createAsyncThunk(
    'customer/fetch',
    async(_,thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await fetchCustomersService(token)
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        }
    }
)


export const createCustomer = createAsyncThunk(
    'customer/create',
    async(customer, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await createCustomerService(token, customer)
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        }
    }
)


export const updateCustomer = createAsyncThunk(
    'customer/update',
    async(customer, thunkAPI)  => {
        try{ 
            const token = thunkAPI.getState().auth.token
            return await updateCustomerService(token, customer)
        }catch(error){
            return thunkAPI.rejectWithValue(thunkAPI.error.data)
        }
    }
)

export const deleteCustomer = createAsyncThunk(
    'customer/delete',
    async(id, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            await deleteCustomerService(token, id)
            return id
        }catch(error){
            return thunkAPI.rejectWithValue(thunkAPI.error.data)
        }
    }
)


export const customerSlice = createSlice({
    name:"customer",
    initialState,
    reducers:{
        reset:(state) => initialState
    },
    extraReducers: {
        [fetchCustomers.pending]: (state) => {
            state.loading = true
        },   
        [fetchCustomers.fulfilled]: (state, action) => {
            state.loading = false
            state.error = null
            state.customers = action.payload.customers
        },
        [fetchCustomers.rejected]: (state, action) => {
            state.loading = false
            state.error = action.payload
        },
        [updateCustomer.fulfilled]: (state, action) => {
            state.error = null
            let index = state.customers.findIndex((customer) => customer.id == action.payload.customer.id);
            state.customers[index] = action.payload.customer
        },
        [updateCustomer.rejected]: (state, action) => {
            state.error = action.payload
        },
        [deleteCustomer.fulfilled]: (state, action) => {
            state.error = null
            state.customers = state.customers.filter((customer) => customer.id != action.payload)
        },
        [deleteCustomer.rejected]: (state, action) => {
            state.error = action.payload
        }
    }
})

export const {reset} = customerSlice.actions
export default customerSlice.reducer


