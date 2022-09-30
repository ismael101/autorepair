import axios from 'axios'

const BASE_URL = 'http://localhost:8080/api/v1/customer'

export const fetchCustomersService = async(token) => {
    const config = {
        headers:{
            Authorization:`Bearer ${token}`
        }
    }
    const response = await axios.get(BASE_URL, config)
    return response.data
}


export const createCustomerService = async(token, customer) => {
    const config = {
        headers:{
            Authoriation:`Bearer ${token}`
        }
    }
    const response = await axios.post(BASE_URL, customer, config)
    return response.data
}

export const updateCustomerService = async(token, customer) => {
    const config = {
        headers:{
            Authorization: `Bearer ${token}`
        }
    }
    const response =  await axios.put(BASE_URL + `/${customer.id}`, customer, config) 
    return response.data
}

export const deleteCustomerService = async(token, id) => {
    const config = {
        headers:{
            Authorization:`Bearer ${token}`
        }
    }
    await axios.delete(BASE_URL + `/${id}`, config)
}
 