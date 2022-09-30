import Work from '../components/Work'
import { logout } from '../features/auth/authSlice'
import { fetchWorks, createWork, reset } from '../features/work/workSlice'
import { useEffect, useState } from "react"
import { useSelector, useDispatch } from "react-redux"
import Spinner from '../components/Spinner'
import Error from '../components/Error'
import { useNavigate } from 'react-router-dom'

export default function Works(){
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const [create, setCreate] = useState(false)
    const [title, setTitle] = useState("")
    const [description, setDescription] = useState("")
    const [complete, setComplete] = useState(false)
    const { works, loading, error} = useSelector(
        (state) => state.work
    )
    const {token} = useSelector(
        (state) => state.auth
    )

    const handleCreate = () => {
        setCreate(false)
        setTitle("")
        setDescription("")
        setComplete(false)
        dispatch(createWork({title:title, description:description, complete:complete}))
    }

    useEffect(() => {
        if(!token){
            navigate('/')
        }
        dispatch(fetchWorks())
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
    },[error, token])

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
        <div className='h-fit p-5'>
            <div className='py-2 px-5 flex justify-between'>
                <h1 className='font-bold text-xl'>Works</h1>
                <button onClick={() => setCreate(true)} type="button" className="focus:outline-none text-white bg-green-600 hover:bg-green-700 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5">Create Work</button>
                {
                    create &&    
                    <div className="flex justify-center items-center overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none">  
                        <div className="relative w-2/5 my-6 mx-auto">               
                            <div className='border relative bg-white rounded-lg'>
                                <div className="border-b-2 flex flex-row justify-between p-5">
                                    <h1 className="text-xl font-bold">Create Work</h1>
                                    <button onClick={() => setCreate(false)}  type="button" className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center">
                                        <svg aria-hidden="true" className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fillRule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clipRule="evenodd"></path></svg>
                                    </button>
                                </div>
                                <div>
                                    <form onSubmit={() => handleCreate()}>
                                        <div className="mx-4 mb-1 mt-5"> 
                                            <label htmlFor="title" className="block mb-2 text-sm font-medium">Title</label>
                                            <input value={title} onChange={(e) => {setTitle(e.target.value)}} type="text" id="title" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Taillight Issues" minLength={1} maxLength={20} required/>
                                        </div>
                                        <div className="mx-4 my-1">
                                            <label htmlFor="description" className="block mb-2 text-sm font-medium">Description</label>
                                            <textarea value={description} onChange={(e) => {setDescription(e.target.value)}} rows="4"  type='text' id="description" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Taillights aren't turning red when braking because of internal electrical issues" minLength={10} maxLength={100} required/>
                                        </div>
                                        <div className="mx-4 my-1">
                                            <label htmlFor="complete" className="block mb-2 text-sm font-medium text-gray-900">Set Status</label>
                                            <input checked={complete} onChange={() => {setComplete(!complete)}} type="checkbox" className={`${complete ? "bg-green-600 ":"bg-red-600"} toggle`}/>
                                        </div>
                                        <div className="border-t-2 mt-5 flex justify-end">
                                            <button type="submit" className="m-3 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5">Submit</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                }
            </div>
            <div className='flex flex-col md:flex-row p-5 space-x-5'>
                {works.map((work) => <Work className='w-full md:w-1/3' work={work} key={work.id}/>)}
            </div>
        </div>
    )
}