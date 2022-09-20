import { createSlice } from "@reduxjs/toolkit"


const initialState = {
    vehicles:[],
    isError:false,
    isSuccess:false,
    isLoading:false,
    message:""
}

export const fetchVehicles = createAsyncThunk(
    'vehicle/fetch',
    async(thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            const response = await axios.get(`http://localhost:8080/api/v1/vehicle`, config)
            return response.data
        }catch(error){
            thunkAPI.rejectValueWith(error.data)
        }
    }
)

export const createVehicle = createAsyncThunk(
    'vehicle/fetch',
    async(vehicle, thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            const response = await axios.post(`http://localhost:8080/api/v1/vehicle`, config, vehicle)
            return response.data
        }catch(error){
            thunkAPI.rejectValueWith(error.data)
        }
    }
)


export const updateVehicle = createAsyncThunk(
    'vehicle/update',
    async(id, vehicle, thunkAPI) => {
        try{
            const config = {
                headers:{
                    Authorization:`Bearer ${thunkAPI.state.auth.token}`
                }
            }
            const response = await axios.put(`http://localhost:8080/api/v1/vehicle/${id}`, config, vehicle)
            return response.data
        }catch(error){
            thunkAPI.rejectValueWith(error.data)
        }
    }
)

export const deleteVehicle = createAsyncThunk(
    'vehicle/delete',
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

export const vehicleSlice = createSlice({
    name:"vehicle",
    initialState,
    reducers:{
        reset:(state) => initialState
    },
    extraReducers:(builder) => {
        builder
        .addCase(fetchVehicle.pending, (state) => {
            state.isLoading =  true
        })
        .addCase(createVehicle.pending, (state) => {
            state.isLoading =  true
        })
        .addCase(updateVehicle.pending, (state) => {
            state.isLoading =  true
        })
        .addCase(deleteVehicle.pending, (state) => {
            state.isLoading =  true
        })
        .addCase(fetchVehicles.fulfilled, (state) => {
            state.vehicles = action.payload.vehicles
            state.isLoading = false
            state.isSuccess = true
            state.isError = true
            state.message = "vehicles successfully fetched"
        })
        .addCase(createVehicle.fulfilled, (state, action) => {
            state.vehicles = state.vehicles.push(action.payload.vehicle)
            state.isLoading = false
            state.isSuccess = true
            state.isError = true
            state.message = "vehicle successfully fetched"
        })
        .addCase(updateVehicle.fulfilled, (state, action) => {
            state.vehicles = state.vehicles.forEach(vehicle => {
                if(vehicle.id == action.payload.vehicle.id){
                    vehicle = action.payload.vehicle
                }
            })
            state.isLoading = false
            state.isSuccess = true
            state.isError = true
            state.message = "vehicle successfully created"
        })
        .addCase(deleteVehicle.fulfilled, (state, id) => {
            state.vehicles = state.vehicles.filter(vehicle => vehicle.id == id)
            state.isLoading = false
            state.isSuccess = true
            state.isError = true
            state.message = "vehicle successfully fetched"
        })
        .addCase(fetchVehicles.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })
        .addCase(createVehicle.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })
        .addCase(updateVehicle.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })
        .addCase(deleteVehicle.rejected, (state, action) => {
            state.isLoading = false
            state.isError = true
            state.message = action.payload.error
        })
    }
})

export const {reset} = vehicleSlice.actions
export default vehicleSlice.reducer