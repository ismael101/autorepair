import axios from "axios";

const BASE_URL = 'http://localhost:8080/api/v1/vehicle'

export const fetchVehiclesService = async(token) => {
    const config = {
        headers:{
            Authorization: `Bearer ${token}`
        }
    }
    const response = await axios.get(BASE_URL, config)
    return response.data
}

export const createVehicleService = async(token, vehicle) => {
    const config = {
        headers:{
            Authorization: `Bearer ${token}`
        }
    }
    const response = await axios.post(BASE_URL, vehicle, config)
    return response.data
}

export const updateVehicleService = async(token, vehicle) => {
    const config = {
        headers:{
            Authorization: `Bearer ${token}`
        }
    }
    const response = await axios.put(BASE_URL + `/${vehicle.id}`, vehicle, config)
    return response.data
}

export const deleteVehicleService = async(token, id) => {
    const config = {
        headers:{
            Authorization: `Bearer ${token}`
        }
    }
    await axios.delete(BASE_URL + `/${id}`, config)
}