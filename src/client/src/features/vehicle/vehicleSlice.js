import { createSlice, createAsyncThunk} from "@reduxjs/toolkit"
import { fetchVehiclesService, createVehicleService, updateVehicleService, deleteVehicleService } from './vehicleService'

const initialState = {
    vehicles:[],
    isLoading:false,
    error:null
}

export const fetchVehicles = createAsyncThunk(
    'vehicle/fetch',
    async(_,thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await fetchVehiclesService(token)
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const createVehicle = createAsyncThunk(
    'vehicle/create',
    async(vehicle, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await createVehicleService(token, vehicle)
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const updateVehicle = createAsyncThunk(
    'vehicle/update',
    async(id, vehicle, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await updateVehicleService(token, id, vehicle)
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const deleteVehicle = createAsyncThunk(
    'vehicle/delete',
    async(id, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            await deleteVehicleService(token, id)
            return id
        }catch(error){
            return thunkAPI.rejectWithValue(error.data)
        }
    }
)

export const vehicleSlice = createSlice({
    name:"vehicle",
    initialState,
    reducers:{
        reset:(state) => initialState
    },
    extraReducers:{
        [fetchVehicles.pending]: (state) => {
            state.isLoading = true
        },
        [fetchVehicles.fulfilled]: (state, action) => {
            state.isLoading = false
            state.error = null
            state.vehicles = action.payload.vehicles
        },
        [fetchVehicles.rejected]: (state, action) => {
            state.isLoading = false
            state.error = action.payload
        },
        [updateVehicle.pending]: (state) => {
            state.isLoading = true
        },
        [updateVehicle.fulfilled]: (state, action) => {
            state.isLoading = false
            state.error = null
            state.vehicles.forEach(vehicle => {
                if(vehicle.id == action.payload.vehicle.id){
                    vehicle = action.payload.vehicle
                }
            })
        },
        [updateVehicle.rejected]: (state, action) => {
            state.isLoading = false
            state.error = action.payload
        },
        [deleteVehicle.pending]: (state) => {
            state.isLoading = true
        },
        [deleteVehicle.fulfilled]: (state, action) => {
            state.isLoading = false
            state.error = null
            state.vehicles.filter(vehicle => vehicle.id == action.payload.id)
        },
        [deleteVehicle.rejected]: (state, action) => {
            state.isLoading = false
            state.error = action.payload
        }
    }
})

export const {reset} = vehicleSlice.actions
export default vehicleSlice.reducer