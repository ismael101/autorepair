import axios from "axios";

const BASE_URL = 'http://localhost:8080/api/v1/part'

export const fetchPartsService = async(token) => {
    const config = {
        headers:{
            Authorization: `Bearer ${token}`
        }
    }
    const response = await axios.get(BASE_URL, config)
    return response.data
}

export const createPartService = async(token, part) => {
    const config = {
        headers:{
            Authorization: `Bearer ${token}`
        }
    }
    const response = await axios.post(BASE_URL, part, config) 
    return response.data
}

export const updatePartService = async(token, part) => {
    const config = {
        headers:{
            Authorization: `Bearer ${token}`
        }
    }
    const response =  await axios.put(BASE_URL + `/${part.id}`, part, config) 
    return response.data
}

export const deletePartService = async(token, id) => {
    const config = {
        headers:{
            Authorization: `Bearer ${token}`
        }
    }
    await axios.delete(BASE_URL + `/${id}`, config) 
}