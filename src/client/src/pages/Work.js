import {useParams, useNavigate} from 'react-router-dom'
import { useEffect } from 'react'
import Customer from '../components/Customer'
import Vehicle from '../components/Vehicle'
import Insurance from '../components/Insurance'
import Part from '../components/Part'
import Labor from '../components/Labor'
import { useDispatch, useSelector } from "react-redux"
import { fetchWork } from '../features/work/workSlice'
import Spinner from '../components/Spinner'


export default function Work(){
    const params = useParams()
    const dispatch = useDispatch()
    const navigate = useNavigate()
    const {works, loading, error} = useSelector(
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
            if(error.status = 401){
                navigate('/')
            }
        }
        dispatch(fetchWork(params.id))
    },[token, navigate, error])

    if(loading){
        return(
            <Spinner/>
        )
    }

    return(
        <div className='h-fit p-5'>
            <section className='flex flex-row space-x-3 h-1/3 mb-4'>
                <div className='w-2/5 h-52 rounded-md bg-white flex'>
                    <h1>{works[0].title}</h1>
                    <h1>{works[0].description}</h1>
                </div>
                {works[0].customer ? 
                <Customer customer={works[0].customer}/>
                :
                <div className='w-1/5 h-52 rounded-md bg-white flex'>
                    <h1 className='text-2xl mx-auto my-auto'>No Customer Found</h1>
                </div>
                }
                {works[0].vehicle ? 
                <Vehicle vehicle={works[0].vehicle}/>
                :
                <div className='w-1/5 h-52 rounded-md bg-white flex'>
                    <h1 className='text-2xl mx-auto my-auto'>No Vehicle Found</h1>
                </div>
                }
                {works[0].vehicle ? 
                <Insurance Insurance={works[0].insurance}/>
                :
                <div className='w-1/5 h-52 rounded-md bg-white flex'>
                    <h1 className='text-2xl mx-auto my-auto'>No Insurance Found</h1>
                </div>
                }
            </section>
            <section className='h-2/3 flex flex-row space-x-3'>
                <div className='flex h-full rounded-md w-1/2 bg-white'>
                    {works[0].labors.length == 0 ? 
                    <h1 className='mx-auto my-auto text-3xl'>Labor Not Found</h1>
                    :
                    works[0].labors.forEach(labor => {
                        <Labor labor={labor}/>
                    })    
                    }
                </div>
                <div className='flex h-96 rounded-md w-1/2 bg-white'>
                    {works[0].parts.length == 0 ? 
                    <h1 className='mx-auto my-auto text-3xl'>Part Not Found</h1>
                    :
                    works[0].parts.forEach(part => {
                        <Part part={part}/>
                    })    
                    }
                </div>
            </section>
        </div>
    )
}