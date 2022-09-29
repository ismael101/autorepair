import { useEffect } from "react"
import { useDispatch, useSelector } from "react-redux"
import { useNavigate } from "react-router-dom"
import Error from "../components/Error"
import Part from "../components/Part"
import Spinner from "../components/Spinner"
import { logout } from "../features/auth/authSlice"
import { fetchParts, reset } from "../features/part/partSlice"

export default function Parts(){
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const {token} = useSelector(
        (state) => state.auth
    )
    const {loading, error, parts} = useSelector(
        (state) => state.part
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
        dispatch(fetchParts())
    },[token, navigate, error])
    

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
                <h1 className="text-xl font-bold">Parts</h1>
            </div>
            <div className="flex flex-col p-5">
                {
                    parts.map(part => <Part part={part} key={part.id}/>)
                }
            </div>
        </div>
    )
}