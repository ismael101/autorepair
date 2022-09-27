import axios from "axios";

const BASE_URL = 'http://localhost:8080/api/v1/work'

export const fetchWorksService = async(token) => {
    const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    const response = await axios.get(BASE_URL, config)
    return response.data
}

export const fetchWorkService = async(token, id) => {
  const config = {
    headers:{
      Authorization: `Bearer ${token}`
    }
  }
  const response = await axios.get(BASE_URL + `/${id}`, config)
  return response.data
}

export const createWorkService = async(token, work) => {
    const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    const response = await axios.post(BASE_URL, work, config)
    return response.data
}

export const updateWorkService = async(token, id, work) => {
    const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    const response = await axios.put(BASE_URL + `/${id}`, config, work)
    return response.data
}

export const deleteWorkService = async(token, id) => {
    const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    const response = await axios.delete(BASE_URL + `/${id}`, config)
    return response.data
}