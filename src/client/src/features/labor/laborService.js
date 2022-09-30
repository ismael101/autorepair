import axios from "axios";

const BASE_URL = 'http://localhost:8080/api/v1/labor'

export const fetchLaborsService = async(token) => {
    const config = {
        headers:{
            Authorization: `Bearer ${token}`
        }
    }
    const response = await axios.get(BASE_URL, config)
    return response.data
}

export const createLaborService = async(token, labor) => {
    const config = {
        header:{
            Authorization: `Bearer ${token}`
        }
    }
    const response = await axios.post(BASE_URL, labor, config)
    return response.data  
}

export const updateLaborService = async(token, labor) => {
    const config = {
        headers:{
            Authorization: `Bearer ${token}`
        }
    }
    const response =  await axios.put(BASE_URL + `/${labor.id}`, labor, config) 
    return response.data
}

export const deleteLaborService = async(token, id) => {
    const config = {
        headers:{
            Authorization: `Bearer ${token}`
        }
    }
    await axios.delete(BASE_URL + `/${id}`, config) 
}