import { useState } from "react"
import { useDispatch } from "react-redux"
import { createInsurance } from '../../../features/insurance/insuranceSlice'

export default function AddInsurance(props){
    const dispatch = useDispatch()
    const [provider, setProvider] = useState("")
    const [policy, setPolicy] = useState("")
    const [vin, setVin] = useState("")

    const handleSubmit = () => {
        dispatch(createInsurance({provider:provider, policy:policy, vin:vin, work:props.work}))
    }

    return(
        <div>
        <label htmlFor="add_insurance" className="btn no-animation normal-case text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium">Create Insurance</label>
        <input type="checkbox" id="add_insurance" className="modal-toggle" />
        <div className="modal">
        <div className="modal-box relative bg-white">
            <label htmlFor="add_insurance" className="btn btn-sm btn-circle absolute right-2 top-2">✕</label>
            <h3 className="text-lg font-bold text-black">Create Insurance</h3>
            <form className='m-5 space-y-5' onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="provider" className="block mb-2 text-sm font-medium text-gray-900">Enter Provider</label>
                    <input onChange={(e) => {setProvider(e.target.value)}} value={provider} type="text" id="first" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Geico" required/>
                </div>
                <div>
                    <label htmlFor="policy" className="block mb-2 text-sm font-medium text-gray-900">Enter Policy</label>
                    <input onChange={(e) => {setPolicy(e.target.value)}} value={policy} type="text" id="last" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="92JIU3RF9U2NC9WUDJIUN" required/>
                </div>
                <div>
                    <label htmlFor="vin" className="block mb-2 text-sm font-medium text-gray-900">Enter Vin</label>
                    <input onChange={(e) => {setVin(e.target.value)}} value={vin} type="text" id="text" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="UIWNEDCUN83YFKWJNSDKCJNSDXWD" required/>
                </div>
                <button htmlFor="add_insurance" type='submit' className='btn no-animation normal-case text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium'>Submit</button>
            </form>
        </div>
        </div>
        </div>
    )
}