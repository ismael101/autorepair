import { useState } from "react"
import { useDispatch } from "react-redux"
import { createCustomer } from '../../../features/customer/customerSlice'


export default function AddCustomer(props){
    const [first, setFirst] = useState("")
    const [last, setLast] = useState("")
    const [email, setEmail] = useState("")
    const [phone, setPhone] = useState()
    const dispatch = useDispatch()

    const handleSubmit = () => {
        dispatch(createCustomer({first:first, last:last, email:email, phone:phone, work:props.work}))
    }

    return(
        <div>
        <label htmlFor="add_customer" className="btn no-animation normal-case text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium">Create Customer</label>
        <input type="checkbox" id="add_customer" className="modal-toggle" />
        <div className="modal">
        <div className="modal-box relative bg-white">
            <label htmlFor="add_customer" className="btn btn-sm btn-circle absolute right-2 top-2">✕</label>
            <h3 className="text-lg font-bold text-black">Create Customer</h3>
            <form className='m-5 space-y-5' onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="" className="block mb-2 text-sm font-medium text-gray-900">Enter First Name</label>
                    <input onChange={(e) => {setFirst(e.target.value)}} value={first} type="text" id="first" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Broken Tail Light" required/>
                </div>
                <div>
                    <label htmlFor="last" className="block mb-2 text-sm font-medium text-gray-900">Enter Last Name</label>
                    <input onChange={(e) => {setLast(e.target.value)}} value={last} type="text" id="last" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Broken Tail Light" required/>
                </div>
                <div>
                    <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-900">Enter Email</label>
                    <input onChange={(e) => {setEmail(e.target.value)}} value={email} type="email" id="email" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Broken Tail Light" required/>
                </div>
                <div>
                    <label htmlFor="phone" className="block mb-2 text-sm font-medium text-gray-900">Enter Phone Number</label>
                    <input onChange={(e) => {setPhone(e.target.value)}} value={phone} type="number" id="phone" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Broken Tail Light" required/>
                </div>
                <button htmlFor="add_customer" type='submit' className='btn no-animation normal-case text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium'>Submit</button>
            </form>
        </div>
        </div>
        </div>
    )
}