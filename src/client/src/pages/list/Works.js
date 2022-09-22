import Work from '../../components/Work'
import { useEffect, useState } from "react"
import { useSelector } from "react-redux"
import AddWork from '../../components/modals/AddWork'
import { useNavigate } from "react-router-dom"
import Spinner from '../../components/Spinner'
import Error from '../../components/Error'
import axios from 'axios'

export default function Works(){
    const [works, setWorks] = useState()
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState()
    const {token} = useSelector(
        (state) => state.auth
    )
    const navigate = useNavigate()

    useEffect(() => {
        try{
            setLoading(true)
            const config = {
                headers:{
                    Authorization: `Bearer ${token}`
                }
            }
            const response = axios.get('http://localhost:8080/api/v1/work', config)
            setWorks(response.data.works)
            setLoading(false)
        }catch(error){
            setLoading(false)
            setError(error.response.data)
        }
    },[])

    useEffect(() => {
        if(!token){
            navigate('/')
        }
    },[token, navigate])

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