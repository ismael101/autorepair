import { useState } from "react"
import { useDispatch } from "react-redux"
import { updateCustomer, deleteCustomer } from "../features/customer/customerSlice"


export default function Customer(props){
    const dispatch = useDispatch()
    const [edit, setEdit] = useState(false)
    const [del, setDel] = useState(false)
    const [first, setFirst] = useState(props.customer.first)
    const [last, setLast] = useState(props.customer.last)
    const [email, setEmail] = useState(props.customer.email)
    const [phone, setPhone] = useState(props.customer.phone)

    const handleUpdate = () => {
        dispatch(updateCustomer(props.customer.id, {first:first, last:last, email:email, phone:phone}))
    }

    const handleDelete = () => {
        dispatch(deleteCustomer(props.customer.id))
    }


    return( 
        <div className='bg-white flex flex-col space-y-2 rounded-lg p-4 w-1/3 shadow-md hover:shadow-lg'>
        <div className="flex flex-row justify-between">
            <div className="flex flex-col">
                <h1 className="text-sm font-thin">Name:</h1>
                <h1>{props.customer.first} {props.customer.last}</h1>
            </div>
            <div className="flex flex-row">
            <div>
                <svg onClick={() => {setEdit(true)}} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5 text-black cursor-pointer">
                <path strokeLinecap="round" strokeLinejoin="round" d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10" />
                </svg>
                {
                    edit &&    
                    <div className="flex justify-center items-center overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none">  
                        <div className="relative w-2/5 my-6 mx-auto">               
                            <div className='border relative bg-white rounded-lg'>
                                <div className="border-b-2 flex flex-row justify-between p-5">
                                    <h1 className="text-xl font-bold">Edit Customer</h1>
                                    <button onClick={() => {setEdit(false)}}  type="button" className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center">
                                        <svg aria-hidden="true" className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fillRule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clipRule="evenodd"></path></svg>
                                    </button>
                                </div>
                                <div>
                                    <form onSubmit={() => {handleUpdate()}}>
                                        <div className="mx-4 mb-1 mt-5"> 
                                            <label htmlFor="first" className="block mb-2 text-sm font-medium">First</label>
                                            <input value={first} onChange={(e) => {setFirst(e.target.value)}} type="text" id="first" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Toyota" required/>
                                        </div>
                                        <div className="mx-4 my-1">
                                            <label htmlFor="last" className="block mb-2 text-sm font-medium">Last</label>
                                            <input value={last} onChange={(e) => {setLast(e.target.value)}} type="text" id="last" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Camry" required/>
                                        </div>
                                        <div className="mx-4 my-1">
                                            <label htmlFor="email" className="block mb-2 text-sm font-medium">Email</label>
                                            <input value={email} onChange={(e) => {setEmail(e.target.value)}} type="email" id="email" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="2017" required/>
                                        </div>
                                        <div className="mx-4 my-1">
                                            <label htmlFor="phone" className="block mb-2 text-sm font-medium">Phone</label>
                                            <input value={phone} onChange={(e) => {setPhone(e.target.value)}} type="phone" id="phone" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="2017" required/>
                                        </div>
                                        <div className="border-t-2 mt-5 flex justify-end">
                                            <button type="button" className="m-3 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5">Submit</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                }
                </div>
                <div>
                    <svg onClick={() => {setDel(true)}} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5 text-red-500 cursor-pointer">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0" />
                    </svg>
                {
                    del &&    
                    <div className="flex justify-center items-center overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none">  
                        <div className="relative w-2/5 my-6 mx-auto">               
                            <div className='border relative bg-white rounded-lg'>
                                <button onClick={() => {setDel(false)}}  type="button" className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center absolute right-2 top-2">
                                    <svg aria-hidden="true" className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fillRule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clipRule="evenodd"></path></svg>
                                </button>
                                <div className="flex flex-col space-y-5 p-5">
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="mx-auto w-40 text-red-600">
                                        <path strokeLinecap="round" strokeLinejoin="round" d="M12 9v3.75m9-.75a9 9 0 11-18 0 9 9 0 0118 0zm-9 3.75h.008v.008H12v-.008z" />
                                    </svg>
                                    <h1 className="text-center text-2xl">Are You Sure You Want To Delete Customer?</h1>
                                    <div className="mx-auto">
                                        <button onClick={() => {setDel(false)}} type="button" className="focus:outline-none text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2">Cancel</button>
                                        <button onClick={() => {handleDelete()}} type="button" className="focus:outline-none text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5">Delete</button>
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
            <h1 className="text-sm font-thin">Email;</h1>
            <h1>{props.customer.email}</h1>
        </div>
        <div className="flex flex-col">
            <h1 className="text-sm font-thin">Phone:</h1>
            <h1>{props.customer.phone}</h1>
        </div>
        <div className="flex flex-row justify-between">
            <div className="flex flex-col">
                <h1 className="text-sm font-thin">Created:</h1>
                <h1>{props.customer.createdAt.substring(0,10)}</h1>
            </div>
            <div className="flex flex-col">
                <h1 className="text-sm font-thin">Updated:</h1>
                <h1>{props.customer.updatedAt.substring(0,10)}</h1>
            </div>
        </div>
        </div>
    )
}