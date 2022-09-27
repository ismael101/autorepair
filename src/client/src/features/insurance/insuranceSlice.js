import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import { fetchInsurancesService, createInsuranceService, updateInsuranceService, deleteInsuranceService } from './insuranceService'

const initialState = {
    insurances:[],
    isLoading: false,
    error:null
}

export const fetchInsurances = createAsyncThunk(
    'insurance/fetch',
    async(_,thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await fetchInsurancesService(token)
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const createInsurance = createAsyncThunk(
    'insurance/create',
    async(insurance, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await createInsuranceService(token, insurance)
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const updateInsurance = createAsyncThunk(
    'insurance/update',
    async(id, insurance, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await updateInsuranceService(token, id, insurance)
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const deleteInsurance = createAsyncThunk(
    'insurance/delete',
    async(id, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            await deleteInsuranceService(token, id)
            return id
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const insuranceSlice = createSlice({
    name:"insurance",
    initialState,
    reducers:{
        reset:(state) => initialState
    },
    extraReducers:{
        [fetchInsurances.pending]: (state) => {
            state.isLoading = true
        },
        [fetchInsurances.fulfilled]: (state, action) => {
            state.isLoading = false
            state.error = null
            state.insurances = action.payload.insurances
        },
        [fetchInsurances.rejected]: (state, action) => {
            state.isLoading = false
            state.error = action.payload
        },
        [updateInsurance.pending]: (state) => {
            state.isLoading = true
        },
        [updateInsurance.fulfilled]: (state, action) => {
            state.isLoading = false
            state.error = null
            state.insurances.forEach(insurance => {
                if(insurance.id == action.payload.insurance.id){
                    insurance = action.payload.insurance
                }
            })
        },
        [updateInsurance.rejected]: (state, action) => {
            state.isLoading = false
            state.error = action.payload
        },
        [deleteInsurance.pending]: (state) => {
            state.isLoading = true
        },
        [deleteInsurance.fulfilled]: (state, action) => {
            state.isLoading = false
            state.error = null
            state.insurances.filter(insurance => insurance.id == action.payload.id)
        },
        [deleteInsurance.rejected]: (state, action) => {
            state.isLoading = false
            state.error = action.payload
        }
    }
})

export const {reset} = insuranceSlice.actions
export default insuranceSlice.reducer