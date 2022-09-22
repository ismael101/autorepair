import axios from "axios";

const BASE_URL = 'http://localhost:8080/api/v1/auth'

export const loginService = async(user) => {
    const response = await axios.post(BASE_URL + '/login', user)
    if(response.data){
        localStorage.setItem('token', response.data.token)
    }
    return response.data
}

export const signupService = async(user) => {
    const response = await axios.post(BASE_URL + '/signup', user)
    return response.data
}

export const logoutService = () => {
    localStorage.removeItem('token')
}