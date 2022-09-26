import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import Vehicle from "../../components/Vehicle";
import Spinner from "../../components/Spinner";
import Error from "../../components/Error";
import axios from "axios";

export default function Vehicles(){
    const [vehicles, setVehicles] = useState()
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState()
    const navigate = useNavigate()
    const {token} = useSelector(
        (state) => state.auth 
    )

    useEffect(() => {
        if(!token){
            navigate('/')
        }
    },[token, navigate])

    useEffect(() => {
        fetchVehicles()
    },[])

    const fetchVehicles = async() => {
        try{
            setLoading(true)
            const config = {
                headers:{
                    Authorization:`Bearer ${token}`
                }
            }
            const response = await axios.get('http://localhost:8080/api/v1/vehicle', config)
            setVehicles(response.data.vehicles)
            setLoading(false)
        }catch(error){
            setLoading(false)
            setError(error.response.data)
        }
    }

    if(loading){
        <Spinner/>
    }

    if(error){
        <Error error={error}/>
    }

    return(
        <div className="h-screen p-10 flex flex-row">
            {vehicles.map(vehicle => {
                return <Vehicle key={vehicle.id} vehicle={vehicle}/>
            })}
        </div>
    )

}