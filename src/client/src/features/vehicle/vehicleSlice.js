import { createSlice, createAsyncThunk} from "@reduxjs/toolkit"
import { fetchVehiclesService, createVehicleService, updateVehicleService, deleteVehicleService } from './vehicleService'

const initialState = {
    vehicles:[],
    loading:false,
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
    async(vehicle, thunkAPI) => {
        try{
            const token = thunkAPI.getState().auth.token
            return await updateVehicleService(token, vehicle)
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
            state.loading = true
        },
        [fetchVehicles.fulfilled]: (state, action) => {
            state.loading = false
            state.error = null
            state.vehicles = action.payload.vehicles
        },
        [fetchVehicles.rejected]: (state, action) => {
            state.loading = false
            state.error = action.payload
        },
        [updateVehicle.fulfilled]: (state, action) => {
            state.error = null
            let index = state.vehicles.findIndex((vehicle) => vehicle.id == action.payload.vehicle.id);
            state.vehicles[index] = action.payload.vehicle
        },
        [updateVehicle.rejected]: (state, action) => {
            state.error = action.payload
        },
        [deleteVehicle.fulfilled]: (state, action) => {
            state.error = null
            state.vehicles = state.vehicles.filter((vehicle) => vehicle.id != action.payload)
        },
        [deleteVehicle.rejected]: (state, action) => {
            state.error = action.payload
        }
    }
})

export const {reset} = vehicleSlice.actions
export default vehicleSlice.reducer