import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { fetchCustomers, reset } from "../features/customer/customerSlice";
import { logout } from "../features/auth/authSlice";
import Spinner from "../components/Spinner";
import Error from "../components/Error";
import Customer from "../components/Customer";

export default function Customers(){
    const dispatch = useDispatch()
    const navigate = useNavigate()
    const {customers, loading, error} = useSelector(
        (state) => state.customer
    )
    const {token} = useSelector(
        (state) => state.auth
    )

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
        dispatch(fetchCustomers())
    },[token, navigate, error, dispatch])

    if(loading){
        return(
            <Spinner/>
        )
    }

    if(error){
        return(
            <Error error={error}/>
        )
    }

    return(
        <div className="h-fit p-5">
            <div className="py-2 px-5 flex">
                <h1 className="text-xl font-bold">Customers</h1>
            </div>
            <div className="flex flex-row p-5 space-x-5">
                {customers.map(customer => <Customer customer={customer} key={customer.id} />)}
            </div>
        </div>
    )
}