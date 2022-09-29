import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logout } from "../features/auth/authSlice";
import { fetchLabors, reset } from '../features/labor/laborSlice';
import Labor from "../components/Labor";
import Error from "../components/Error";
import Spinner from "../components/Spinner";

export default function Labors(){
    const dispatch = useDispatch()
    const navigate = useNavigate()
    const {token} = useSelector(
        (state) => state.auth
    )
    const { labors, loading, error } = useSelector(
        (state) => state.labor
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
        dispatch(fetchLabors())
    },[navigate, dispatch, error, token])

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
                <h1 className="text-xl font-bold">Labors</h1>
            </div>
            <div className="flex flex-col p-5">
                {
                    labors.map(labor => <Labor labor={labor} key={labor.id} />)
                }
            </div>
        </div>
    )

}