import { useState } from "react"
import { updateWork, deleteWork } from "../features/work/workSlice" 
import { useDispatch } from "react-redux"
import { useNavigate } from "react-router-dom"

export default function Work(props){
    const dispatch = useDispatch()
    const [edit, setEdit] = useState(false)
    const [del, setDel] = useState(false)
    const [title, setTitle] = useState(props.work.title)
    const [description, setDescription] = useState(props.work.description)
    const [complete, setComplete] = useState(props.work.complete)

    const handleUpdate = () => {
        setEdit(false)
        dispatch(updateWork({id:props.work.id, title:title, description:description, complete:complete}))
    }

    const handleDelete = () => {
        setDel(false)
        dispatch(deleteWork(props.work.id))
    }

    return(
            <div className={`bg-white flex flex-col space-y-2 rounded-lg p-4 shadow-md hover:shadow-lg border-2 cursor-pointer ${props.work.complete ? "border-green-600" : "border-red-600"}`}>
                <div className="flex flex-row justify-between">
                    <div className="flex flex-col">
                        <h1 className="font-thin text-sm">Title:</h1>
                        <h1>{props.work.title}</h1>
                    </div>
                    <div className="flex flex-row">
                    <div>
                        <svg onClick={() => setEdit(true)} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5 text-black cursor-pointer">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10" />
                        </svg>
                        {
                            edit &&    
                            <div className="flex justify-center items-center overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none">  
                                <div className="relative w-2/5 my-6 mx-auto">               
                                    <div className='border relative bg-white rounded-lg'>
                                        <div className="border-b-2 flex flex-row justify-between p-5">
                                            <h1 className="text-xl font-bold">Edit Work</h1>
                                            <button onClick={() => setEdit(false)}  type="button" className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center">
                                                <svg aria-hidden="true" className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fillRule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clipRule="evenodd"></path></svg>
                                            </button>
                                        </div>
                                        <div>
                                            <form onSubmit={() => handleUpdate()}>
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
                        <div>
                            <svg onClick={() => setDel(true)} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5 text-red-500 cursor-pointer">
                                <path strokeLinecap="round" strokeLinejoin="round" d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0" />
                            </svg>
                        {
                            del &&    
                            <div className="flex justify-center items-center overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none">  
                                <div className="relative w-2/5 my-6 mx-auto">               
                                    <div className='border relative bg-white rounded-lg'>
                                        <button onClick={() => setDel(false)}  type="button" className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center absolute right-2 top-2">
                                            <svg aria-hidden="true" className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fillRule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clipRule="evenodd"></path></svg>
                                        </button>
                                        <div className="flex flex-col space-y-5 p-5">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="mx-auto w-40 text-red-600">
                                                <path strokeLinecap="round" strokeLinejoin="round" d="M12 9v3.75m9-.75a9 9 0 11-18 0 9 9 0 0118 0zm-9 3.75h.008v.008H12v-.008z" />
                                            </svg>
                                            <h1 className="text-center text-2xl">Are You Sure You Want To Delete Work?</h1>
                                            <div className="mx-auto">
                                                <button onClick={() => setDel(false)} type="button" className="focus:outline-none text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2">Cancel</button>
                                                <button onClick={() => handleDelete()} type="button" className="focus:outline-none text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5">Delete</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        }
                        </div>
                    </div>
                </div>
                <div className="flex flex-col">
                    <h1 className="font-thin text-sm">Description</h1>
                    <h1>{props.work.description}</h1>
                </div>
                <div className="flex flex-row">
                    <div className="flex flex-col w-1/3">
                        <h1 className="font-thin text-sm">Customer</h1>
                        {props.work.customer ? <h1>{props.work.customer.first} {props.work.customer.last}</h1> : <h1>null</h1>}
                    </div>
                    <div className="flex flex-col w-1/3">
                        <h1 className="font-thin text-sm">Vehicle</h1>
                        {props.work.vehicle ? <h1>{props.work.vehicle.make} {props.work.vehicle.model}</h1> : <h1>null</h1>}
                    </div>
                    <div className="flex flex-col w-1/3">
                        <h1 className="font-thin text-sm">Insurance</h1>
                        {props.work.insurance ? <h1>{props.work.insurance.provider}</h1> : <h1>null</h1>}
                    </div>
                </div>
                <div className="flex flex-row">
                    <div className="flex flex-col w-1/3">
                        <h1 className="font-thin text-sm">Labor</h1>
                        <h1>{props.work.labors.length}</h1>
                    </div>
                    <div className="flex flex-col w-1/3">
                        <h1 className="font-thin text-sm">Parts</h1>
                        <h1>{props.work.parts.length}</h1>
                    </div>
                </div>
                <div className="flex flex-row justify-between">
                <div className="flex flex-col">
                        <h1 className="font-thin text-sm">Created</h1>
                        <h1>{props.work.createdAt.substring(0,10)}</h1>
                    </div>
                    <div className="flex flex-col">
                        <h1 className="font-thin text-sm">Updated</h1>
                        <h1>{props.work.updatedAt.substring(0,10)}</h1>
                    </div>
                </div>
            </div>
    )
}