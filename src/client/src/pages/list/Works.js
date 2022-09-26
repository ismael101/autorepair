import Work from '../../components/Work'
import { logout } from '../../features/auth/authSlice'
import { useEffect, useState } from "react"
import { useSelector, useDispatch } from "react-redux"
import AddWork from '../../components/modals/AddWork'
import { useNavigate } from "react-router-dom"
import Spinner from '../../components/Spinner'
import Error from '../../components/Error'

export default function Works(){
    const dispatch = useDispatch()
    const { customers, isLoading, isError, error} = useSelector(
        (state) => state.customer
    )
    const {token} = useSelector(
        (state) => state.auth
    )
    const navigate = useNavigate()

    useEffect(() => {
        if(!token){
            navigate('/')
        }
    },[token, navigate])

    useEffect(() => {
        if(error){
            if(error.status == 401){
                dispatch(logout())
            }
        }
    },[error])

    useEffect(() => {
        fetchWorks()
    },[])

    if(isLoading){
        return(
            <Spinner/>
        )
    }
    if(isError){
        return(
            <Error error={error}/>
        )
    }
    if(works){
        return(
            <div className='h-screen p-10'>
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
}