import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
import Spinner from "../components/Spinner";
import Error from "../components/Error";
import Vehicle from "../components/Vehicle";
import { fetchVehicles, reset } from "../features/vehicle/vehicleSlice";
import { logout } from "../features/auth/authSlice";

export default function Vehicles(){
    const dispatch = useDispatch()
    const navigate = useNavigate()
    const {token} = useSelector(
        (state) => state.auth 
    )
    const {vehicles, loading, error} = useSelector(
        (state) => state.vehicle
    )

    useEffect(() => {
        if(!token){
            navigate('/')
        }
        dispatch(fetchVehicles())
    },[])

    useEffect(() => {
        if(!token){
            navigate('/')
        }
        if(error){
            if(error.status == 401){
                dispatch(reset())
                dispatch(logout())
            }
        }
    },[token, error])

    if(loading){
        <Spinner/>
    }

    if(error){
        <Error error={error}/>
    }

    return(
        <div className="h-fit p-5">
            <div className="py-2 px-5 flex">
                <h1 className="text-xl font-bold">Vehicles</h1>
            </div>
            <div className="flex flex-row p-5 space-x-5">
                {vehicles.map(vehicle => <Vehicle vehicle={vehicle} key={vehicle.id} />)}
            </div>
        </div>
    )
}
