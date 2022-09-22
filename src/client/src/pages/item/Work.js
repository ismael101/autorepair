import {useParams, useNavigate} from 'react-router-dom'
import { useEffect, useState } from 'react'
import Customer from '../../components/Customer'
import Vehicle from '../../components/Vehicle'
import Insurance from '../../components/Insurance'
import Part from '../../components/Part'
import Labor from '../../components/Labor'
import { useSelector } from "react-redux"
import axios from 'axios'
import Spinner from '../../components/Spinner'


export default function Work(){
    const params = useParams()
    const navigate = useNavigate()
    const [work, setWork] = useState()
    const [error, setError] = useState()
    const[loading, setLoading] = useState(false)

    const {token} = useSelector(
        (state) => state.auth
    )

    useEffect(() => {
        fetchWork()
    },[])

    useEffect(() => {
        if(!token){
            navigate('/')
        }
    },[token])

    const fetchWork = async() => {
        try{
            setLoading(true)
            const id = params.id
            const response = axios.get(`http://localhost:8080/api/v1/work/${id}`)
            setLoading(false)
            setWork(response.data.work)
        }catch(error){
            setLoading(false)
            setError(error.repsonse.data)
        }
    }

    if(loading){
        return(
            <Spinner/>
        )
    }
    return(
        <div className='h-screen p-5'>
        <div className='flex flex-row'>
            <div className='bg-white p-5 rounded-md'>
                <h1>{work.title}</h1>
                <p>{work.description}</p>
            </div>
            {
                work.customer ?
                <Customer customer={work.customer} />
                :
                <div className='bg-white p-5 rounded-md flex'>
                    <h1 className='mx-auto my-auto text-black'>No Customer Found</h1>
                </div>
            }
            {
                work.vehicle ?
                <Vehicle vehicle={work.vehicle} />
                :
                <div className='bg-white p-5 rounded-md flex'>
                    <h1 className='mx-auto my-auto text-black'>No Vehicle Found</h1>
                </div>
            }
            {
                work.insurance ?
                <Insurance insurance={work.insurance} />
                :
                <div className='bg-white p-5 rounded-md flex'>
                    <h1 className='mx-auto my-auto text-black'>No Insurance Found</h1>
                </div>
            }
        </div>
        <div className='flex flex-row'>
            {
                work.labors.length == 0 ?
                <div className='flex'>
                    <h1 className='my-auto mx-auto'>No Labor Found</h1>
                </div>
                :
                <div className='flex flex-col space-y-3'>
                    {works.labors.map((labor) => 
                        <Labor labor={labor} key={labor.id}/>
                    )}
                </div>
            }
            {
                work.parts.length == 0 ?
                <div className='flex'>
                    <h1 className='my-auto mx-auto'>No Part Found</h1>
                </div>
                :
                <div className='flex flex-col space-y-3'>
                    {works.parts.map((part) => 
                        <Part part={part} key={part.id}/>
                    )}
                </div>
            }
        </div>
    </div>
    )
}