import { useState } from "react"
import { useDispatch } from "react-redux"
import { updateInsurance } from "../../../features/insurance/insuranceSlice"

export default function EditInsurance(props){
    const dispatch = useDispatch()
    const [provider, setProvider] = useState(props.insurance.provider)
    const [policy, setPolicy] = useState(props.insurance.policy)
    const [vin, setVin] = useState(props.insurance.vin)

    const handleSubmit = () => {
        dispatch(updateInsurance(props.insurance.id, {provider:provider, policy:policy, vin:vin}))
    }

    return(
        <div>
            <label htmlFor="edit_insurance" className="btn no-animation normal-case text-white bg-purple-700 hover:bg-purple-800 focus:ring-4 focus:outline-none focus:ring-purple-300 font-medium">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-6 h-6">
                    <path strokeLinecap="round" strokeLinejoin="round" d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10" />
                </svg>
            </label>
        <input type="checkbox" id="edit_insurance" className="modal-toggle" />
        <div className="modal">
        <div className="modal-box relative bg-white">
            <label htmlFor="edit_insurance" className="btn btn-sm btn-circle absolute right-2 top-2">✕</label>
            <h3 className="text-lg font-bold text-black">Edit Insurance</h3>
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
                <button htmlFor="edit_insurance" type='submit' className='btn no-animation normal-case text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium'>Submit</button>
            </form>
        </div>
        </div>
        </div>
    )
}