import axios from 'axios'

const BASE_URL = 'http://localhost:8080/api/v1/insurance'

export const fetchInsurancesService = async(token) => {
    const config = {
        headers:{
            Authorization:`Bearer ${token}`
        }
    }
    const response = await axios.get(BASE_URL, config)
    return response.data
}

export const createInsuranceService = async(token, insurance) => {
    const config = {
        headers:{
            Authorization:`Bearer ${token}`
        }
    }
    const response = await axios.post(BASE_URL, insurance, config)
    return response.data
}

export const updateInsuranceService = async(token, id, insurance) => {
    const config = {
        headers:{
            Authorization:`Bearer ${token}`
        }
    }
    const response = await axios.put(BASE_URL + `/${id}`, insurance, config)
    return response.data
}

export const deleteInsuranceService = async(token, id) => {
    const config = {
        headers:{
            Authorization:`Bearer ${token}`
        }
    }
    const response = await axios.delete(BASE_URL + `/${id}`, config)
    return response.data
}