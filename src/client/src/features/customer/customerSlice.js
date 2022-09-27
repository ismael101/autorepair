import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import { fetchCustomersService, updateCustomerService, deleteCustomerService, createCustomerService } from './customerService'

const initialState = {
    customers:[],
    isLoading: false,
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
    async(id, customer, thunkAPI)  => {
        try{ 
            const token = thunkAPI.getState().auth.token
            return await updateCustomerService(token, id, customer)
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
            state.isLoading = true
        },   
        [fetchCustomers.fulfilled]: (state, action) => {
            state.isLoading = false
            state.error = null
            state.customers = action.payload.customers
        },
        [fetchCustomers.rejected]: (state, action) => {
            state.isLoading = false
            state.error = action.payload
        },
        [updateCustomer.pending]: (state) => {
            state.isLoading = true
        },
        [updateCustomer.fulfilled]: (state, action) => {
            state.isLoading = false
            state.error = null
            state.customers.forEach(customer => {
                if(customer.id == action.payload.customer.id){
                    customer = action.payload.customer
                }
            })
        },
        [updateCustomer.rejected]: (state, action) => {
            state.isLoading = false
            state.error = action.payload
        },
        [deleteCustomer.pending]: (state) => {
            state.isLoading = true
        },
        [deleteCustomer.fulfilled]: (state, action) => {
            state.isLoading = false
            state.error = null
            state.customers.filter(customer => customer.id = action.payload.id)
        },
        [deleteCustomer.rejected]: (state, action) => {
            state.isLoading = false
            state.error = action.payload
        }
    }
})

export const {reset} = customerSlice.actions
export default customerSlice.reducer


