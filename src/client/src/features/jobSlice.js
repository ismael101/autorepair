import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

const request = axios.create({
    headers:{'Authorization':`Bearer ${localStorage.getItem('token')}`}
})

const initialState = {
    jobs:[],
    loading:false
}

export const fetchJobsService = createAsyncThunk(
    'job/fetchJobs', 
    async(thunkAPI) => {
        try{
            const res = await request.get('http://localhost:8080/api/v1/job')
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)

export const createJobService = createAsyncThunk(
    'job/createJob', 
    async(job, thunkAPI) => {
        try{
            const res = await request.post('http://localhost:8080/api/v1/job', job)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)

export const updateJobService = createAsyncThunk(
    'job/updateJob', 
    async(id, job, thunkAPI) => {
        try{
            const res = await request.put(`http://localhost:8080/api/v1/job/${id}`, job)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)

export const deleteJobService = createAsyncThunk(
    'job/deleteJob',
    async(id, thunkAPI) => {
        try{
            const res = await request.delete(`http://localhost:8080/api/v1/job/${id}`)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)



export const createAddressService = createAsyncThunk(
    'job/createAddress', 
    async(address, thunkAPI) => {
        try{
            const res = await request.post('http://localhost:8080/api/v1/address', address)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)

export const updateAddressService = createAsyncThunk(
    'job/updateAddress', 
    async(id, address, thunkAPI) => {
        try{
            const res = await request.put(`http://localhost:8080/api/v1/address/${id}`, address)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)


export const deleteAddressService = createAsyncThunk(
    'job/deleteAddress',
    async(id, thunkAPI) => {
        try{
            const res = await request.delete(`http://localhost:8080/api/v1/address/${id}`)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)

export const createCustomerService = createAsyncThunk(
    'job/createCustomer', 
    async(customer, thunkAPI) => {
        try{
            const res = await request.post('http://localhost:8080/api/v1/customer', customer)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)

export const updateCustomerService = createAsyncThunk(
    'job/updateCustomer', 
    async(id, customer, thunkAPI) => {
        try{
            const res = await request.put(`http://localhost:8080/api/v1/customer/${id}`, customer)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)


export const deleteCustomerService = createAsyncThunk(
    'job/deleteCustomer',
    async(id, thunkAPI) => {
        try{
            const res = await request.delete(`http://localhost:8080/api/v1/customer/${id}`)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)

export const createImageService = createAsyncThunk(
    'job/createImage', 
    async(image, thunkAPI) => {
        try{
            const res = await request.post('http://localhost:8080/api/v1/image', image)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)

export const deleteImageService = createAsyncThunk(
    'job/deleteImage',
    async(id, thunkAPI) => {
        try{
            const res = await request.delete(`http://localhost:8080/api/v1/image/${id}`)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)

export const createInsuranceService = createAsyncThunk(
    'job/createInsurance', 
    async(insurance, thunkAPI) => {
        try{
            const res = await request.post('http://localhost:8080/api/v1/insurance', insurance)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)

export const updateInsuranceService = createAsyncThunk(
    'job/updateInsurance', 
    async(id, insurance, thunkAPI) => {
        try{
            const res = await request.put(`http://localhost:8080/api/v1/insurance/${id}`, insurance)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)


export const deleteInsuranceService = createAsyncThunk(
    'job/deleteInsurance',
    async(id, thunkAPI) => {
        try{
            const res = await request.delete(`http://localhost:8080/api/v1/insurance/${id}`)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)

export const createLaborService = createAsyncThunk(
    'job/createLabor', 
    async(labor, thunkAPI) => {
        try{
            const res = await request.post('http://localhost:8080/api/v1/address', labor)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)

export const updateLaborService = createAsyncThunk(
    'job/updateLabor', 
    async(id, labor, thunkAPI) => {
        try{
            const res = await request.put(`http://localhost:8080/api/v1/labor/${id}`, labor)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)


export const deleteLaborService = createAsyncThunk(
    'job/deleteLabor',
    async(id, thunkAPI) => {
        try{
            const res = await request.delete(`http://localhost:8080/api/v1/labor/${id}`)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)

export const createPartService = createAsyncThunk(
    'job/createPart', 
    async(part, thunkAPI) => {
        try{
            const res = await request.post('http://localhost:8080/api/v1/part', part)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)

export const updatePartService = createAsyncThunk(
    'job/createPart', 
    async(id, part, thunkAPI) => {
        try{
            const res = await request.put(`http://localhost:8080/api/v1/part/${id}`, part)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)


export const deletePartService = createAsyncThunk(
    'job/deletePart',
    async(id, thunkAPI) => {
        try{
            const res = await request.delete(`http://localhost:8080/api/v1/part/${id}`)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)

export const createVehicleService = createAsyncThunk(
    'job/createVehicle', 
    async(vehicle, thunkAPI) => {
        try{
            const res = await request.post('http://localhost:8080/api/v1/vehicle', vehicle)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)

export const updateVehicleService = createAsyncThunk(
    'job/createVehicle', 
    async(id, vehicle, thunkAPI) => {
        try{
            const res = await request.put(`http://localhost:8080/api/v1/vehicle/${id}`, vehicle)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)


export const deleteVehicleService = createAsyncThunk(
    'job/deleteVehicle',
    async(id, thunkAPI) => {
        try{
            const res = await request.delete(`http://localhost:8080/api/v1/vehicle/${id}`)
            return res.data
        }
        catch(err){
            return thunkAPI.rejectWithValue(err)
        }
    }
)

const jobSlice = createSlice({
    name:'job',
    initialState,
    reducers:{
        clear: (state) => {
            state.jobs = [],
            state.loading = false
        },
        fetchJobs: (state, action) => {
            state.jobs = action.payload
        },
        addJob: (state, action) => {
            state.jobs = state.jobs.push(action.payload)
        },
        updateJob: (state, action) => {
            state.jobs = state.jobs.forEach(job => {
                if(job.id == action.payload.id){
                    job = action.payload
                }
            })
        },
        deleteJob: (state, action) => {
            state.jobs = state.jobs.filter((job) => job.id !== action.payload.id)
        },
        setAddress: (state, action) => {
            state.jobs = state.jobs.forEach((job) => {
                if(job.id == action.payload.jobId){
                    job.address = action.payload.action
                }
            })
        },
        deleteAddress:(state, action) => {
            state.jobs = state.jobs.forEach((job) => {
                if(job.id == action.payload.jobId){
                    job.address = null
                }
            })
        },
        setCustomer: (state, action) => {
            state.jobs = state.jobs.forEach((job) => {
                if(job.id == action.payload.jobId){
                    job.customer = action.payload.action
                }
            })
        },
        deleteCustomer:(state, action) => {
            state.jobs = state.jobs.forEach((job) => {
                if(job.id == action.payload.jobId){
                    job.customer = null
                }
            })
        },
        setInsurance: (state, action) => {
            state.jobs = state.jobs.forEach((job) => {
                if(job.id == action.payload.jobId){
                    job.insurance = action.payload.action
                }
            })
        },
        deleteInsurance:(state, action) => {
            state.jobs = state.jobs.forEach((job) => {
                if(job.id == action.payload.jobId){
                    job.insurance = null
                }
            })
        },
        setVehicle: (state, action) => {
            state.jobs = state.jobs.forEach((job) => {
                if(job.id == action.payload.jobId){
                    job.vehicle = action.payload
                }
            })
        },
        deleteVehicle:(state, action) => {
            state.jobs = state.jobs.forEach((job) => {
                if(job.id == action.payload.jobId){
                    job.vehicle = null
                }
            })
        },
        addImage: (state, action) => {
            state.jobs = state.jobs.forEach((job) => {
                if(job.id == action.payload.jobId){
                    job.images.push(action.payload)
                }
            })
        },
        deleteImage:(state, action) => {
            state.jobs = state.jobs.forEach((job) => {
                if(job.id == action.payload.jobId){
                    
                }
            })
        },
        addPart:(state, action) => {
            state.jobs = state.jobs.forEach((job) => {
                if(job.id == action.payload.jobId){
                    job.images.push(action.payload)
                }
            })
        },
        setPart:(state, action) => {
            state.jobs = state.jobs.forEach((job) => {
                if(job.id == action.payload.jobId){
                    jobs.parts.forEach((part) => {
                        if(part.id == action.payload.id){
                            part = action.payload
                        }
                    })
                }
            })
        },
        deletePart:(state, action) => {
            state.jobs = state.jobs.forEach((job) => {
                if(job.id == action.payload.jobId){
                    job.parts = job.parts.filter(part => part.id !== action.payload.id)
                }
            })
        },
        addLabor:(state, action) => {
            state.jobs = state.jobs.forEach((job) => {
                if(job.id == action.payload.jobId){
                    job.labors.push(action.payload)
                }
            })
        },
        setLabor:(state, action) => {
            state.jobs = state.jobs.forEach((job) => {
                if(job.id == action.payload.jobId){
                    jobs.labors.forEach((labor) => {
                        if(labor.id == action.payload.id){
                            labor = action.payload
                        }
                    })
                }
            })
        },
        deleteLabor:(state, action) => {
            state.jobs = state.jobs.forEach((job) => {
                if(job.id == action.payload.jobId){
                    job.labors = job.labors.filter(labor => labor.id !== action.payload.id)
                }
            })
        }
    },
    extraReducers:{
        [fetchJobsService.pending]: (state) => {
            state.isLoading = true;
        },
        [fetchJobsService.fulfilled]: (state, action) => {
            state.isLoading = false
            state.jobs = action.payload
        },
        [fetchJobsService.rejected]: (state) => {
            state.isLoading = false
        }
    }
})

export const {clear, fetchJobs, addJob, deleteJob, setAddress, deleteAddress, setCustomer, deleteCustomer, setInsurance, deleteInsurance, setVehicle, deleteVehicle,
addImage, deleteImage, addPart, setPart, deletePart, addLabor, setLabor, deleteLabor} = jobSlice.actions

export default jobSlice.reducer
