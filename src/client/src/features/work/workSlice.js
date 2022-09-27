import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { fetchWorksService, fetchWorkService , createWorkService, updateWorkService, deleteWorkService } from './workService'
import { createCustomer, updateCustomer, deleteCustomer } from '../customer/customerSlice'
import { createInsurance, updateInsurance, deleteInsurance } from '../insurance/insuranceSlice'
import { createVehicle, updateVehicle, deleteVehicle } from '../vehicle/vehicleSlice'
import { createLabor, updateLabor, deleteLabor } from '../labor/laborSlice'
import { createPart, updatePart, deletePart } from '../part/partSlice'

const initialState = {
    works:[],
    loading:false,
    error:null
}

export const fetchWorks = createAsyncThunk(
    'work/fetch',
    async(_,thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await fetchWorksService(token)
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        }
    }
)

export const fetchWork = createAsyncThunk(
    'work/fetch/id',
    async(id, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await fetchWorkService(token, id)
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        } 
    }
)

export const createWork = createAsyncThunk(
    'work/create',
    async(work, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await createWorkService(token, work)
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        }
    }
)

export const updateWork = createAsyncThunk(
    'work/update',
    async(id, work, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await updateWorkService(id, work, token)
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        }
    }
)

export const deleteWork = createAsyncThunk(
    'work/delete',
    async(id, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            await deleteWorkService(id, token)
            return id
        }catch(error){
            return thunkAPI.rejectWithValue(error.response.data)
        }
    }
)


export const workSlice = createSlice({
    name:"work",
    initialState,
    reducers:{
        reset: (state) => initialState,
    },
    extraReducers: {
        //reducers for work object
        [fetchWorks.pending]: (state) => {
            state.loading = true
        },
        [fetchWorks.fulfilled]: (state, action) => {
            state.loading = false
            state.error = false
            state.works = action.payload.works
        },
        [fetchWorks.rejected]: (state, action) => {
            state.loading = false
            state.error = action.payload
        },
        [fetchWork.pending]: (state) => {
            state.loading = true
        },
        [fetchWork.fulfilled]: (state, action) => {
            state.loading = false
            state.error = null
            state.works = [action.payload.work]
        },
        [fetchWork.rejected]: (state, action) => {
            state.loading = false
            state.error = action.payload
        },
        [createWork.pending]: (state) => {
            state.loading = true
        },
        [createWork.fulfilled]: (state, action) => {
            state.loading = false
            state.error = action.payload
            state.works.push(action.payload.work)
        },
        [createWork.rejected]: (state, action) => {
            state.loading = false
            state.error = action.payload
        },
        [updateWork.pending]: (state) => {
            state.loading = true
        },
        [updateWork.fulfilled]: (state, action) => {
            state.loading = false
            state.error = null
            state.works.forEach(work => {
                if(work.id == action.payload.work.id){
                    work = action.payload.work
                }
            })
        },
        [updateWork.rejected]: (state, action) => {
            state.loading = false
            state.error = action.payload
        },
        [deleteWork.pending]: (state) => {
            state.loading = true
        },
        [deleteWork.fulfilled]: (state, action) => {
            state.loading = false
            state.error = null
            state.works.filter(work => work.id == action.payload.id)
        },
        [deleteWork.rejected]: (state, action) => {
            state.loading = false
            state.error = action.payload
        },

        //reducers for customer sub object
        [createCustomer.fulfilled]: (state, action) => {
            state.error = null
            state.works[0].customer = action.payload.customer
        },
        [createCustomer.rejected]: (state, action) => {
            state.error = action.payload
        },
        [updateCustomer.fulfilled]: (state, action) => {
            state.error = null
            state.works[0].customer = action.payload.customer
        },
        [updateCustomer.rejected]: (state, action) => {
            state.error = action.payload
        },
        [deleteCustomer.fulfilled]: (state) => {
            state.error = null
            state.works[0].customer = null
        },
        [deleteCustomer.rejected]: (state, action) => {
            state.error = action.payload
        },

        //reducer for insurance sub object
        [createInsurance.fulfilled]: (state, action) => {
            state.error = null
            state.works[0].insurance = action.payload.insurance
        },
        [createInsurance.rejected]: (state, action) => {
            state.error = action.payload
        },
        [updateInsurance.fulfilled]: (state, action) => {
            state.error = null
            state.works[0].insurance = action.payload.insurance
        },
        [updateInsurance.rejected]: (state, action) => {
            state.error = action.payload
        },
        [deleteInsurance.fulfilled]: (state) => {
            state.error = null
            state.works[0].insurance = null
        },
        [deleteInsurance.rejected]: (state, action) => {
            state.error = action.payload
        },

        //reducer for vehicle sub object
        [createVehicle.fulfilled]: (state, action) => {
            state.error = null
            state.works[0].vehicle = action.payload.vehicle
        },
        [createVehicle.rejected]: (state) => {
            state.error = null
        },
        [updateVehicle.fulfilled]: (state, action) => {
            state.error = null
            state.works[0].vehicle = action.payload.vehicle
        },
        [updateVehicle.rejected]: (state) => {
            state.error = null
        },
        [deleteVehicle.fulfilled]: (state) => {
            state.error = null
            state.works[0].vehicle = null
        },
        [deleteVehicle.rejected]: (state) => {
            state.error = null
        },

        //reducer for labor sub objects
        [createLabor.fulfilled]: (state, action) => {
            state.error = null
            state.works[0].labors.push(action.payload.labor)
        },
        [createLabor.rejected]: (state, action) => {
            state.error = action.payload
        },
        [updateLabor.fulfilled]: (state, action) => {
            state.error = null
            state.works[0].labors.forEach(labor => {
                if(labor.id == action.payload.labor.id){
                    labor = action.payload.labor
                }
            })
        },
        [updateLabor.rejected]: (state, action) => {
            state.error = action.payload
        },
        [deleteLabor.fulfilled]: (state, action) => {
            state.error = null
            state.works[0].labors.filter(labor => labor.id == action.payload.id)
        },
        [deleteLabor.rejected]: (state, action) => {
            state.error = action.payload
        } ,
        
        //reducer for part sub objects
        [createPart.fulfilled]: (state, action) => {
            state.error = null
            state.works[0].parts.push(action.payload.part)
        },
        [createPart.rejected]: (state, action) => {
            state.error = action.payload
        },
        [updatePart.fulfilled]: (state, action) => {
            state.error = null
            state.works[0].parts.forEach(part => {
                if(part.id == action.payload.part.id){
                    part = action.payload.part
                }
            })
        },
        [updatePart.rejected]: (state, action) => {
            state.error = action.payload
        },
        [deletePart.fulfilled]: (state, action) => {
            state.error = null
            state.works[0].parts.filter(part => part.id == action.payload.id)
        },
        [deletePart.rejected]: (state, action) => {
            state.error = action.payload
        }

    }
})

export const {reset} = workSlice.actions
export default workSlice.reducer