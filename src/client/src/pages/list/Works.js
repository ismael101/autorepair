import Work from '../../components/items/Work'
import { logout } from '../../features/auth/authSlice'
import { fetchWorks } from '../../features/work/workSlice'
import { useEffect } from "react"
import { useSelector, useDispatch } from "react-redux"
import AddWork from '../../components/modals/add/AddWork'
import Spinner from '../../components/Spinner'
import Error from '../../components/Error'
import { useNavigate } from 'react-router-dom'

export default function Works(){
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const { works, loading, error} = useSelector(
        (state) => state.work
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
                dispatch(logout())
            }
        }
        dispatch(fetchWorks())
    },[error, token, navigate])

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
        <div className='h-fit p-10'>
            <div className='flex py-2 px-5 justify-between place-items-center'>
                <h1 className='font-bold text-xl text-black'>Work Orders</h1>
                <AddWork/>
            </div>
            <div className='flex p-5 space-x-5'>
                {works.map((work) => 
                    <Work work={work} key={work.id}/>
                )}
            </div>
        </div>
    )
}