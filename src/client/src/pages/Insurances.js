import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logout } from "../features/auth/authSlice";
import { fetchInsurances, reset } from "../features/insurance/insuranceSlice";
import { useEffect } from "react";
import Spinner from "../components/Spinner";
import Insurance from "../components/Insurance";
import Error from "../components/Error";

export default function Insurances(){
    const navigate = useNavigate()
    const dispatch = useDispatch()   
    const {token} = useSelector(
        (state) => state.auth
    )
    const {insurances, loading, error} = useSelector(
        (state) => state.insurance
    )

    useEffect(() => {
        if(!token){
            navigate('/')
        }
        dispatch(fetchInsurances())
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
        return(
            <Spinner/>
        )
    }

    if(error){
        return(
            <Error error={error} />
        )
    }

    return(
        <div className="h-fit p-5">
            <div className="py-2 px-5 flex">
                <h1 className="text-xl font-bold">Insurances</h1>
            </div>
            <div className="flex flex-row p-5 space-x-5">
                {insurances.map(insurance => <Insurance insurance={insurance} key={insurance.id} />)}
            </div>
        </div>
    )
}