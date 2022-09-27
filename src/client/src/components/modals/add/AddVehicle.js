import { useState } from "react";
import { useDispatch } from "react-redux";
import { createVehicle } from '../../../features/vehicle/vehicleSlice'

export default function AddVehicle(props){
    const dispatch = useDispatch()
    const [make, setMake] = useState("")
    const [model, setModel] = useState("")
    const [year, setYear] = useState()
    
    const handleSubmit = () => {
        dispatch(createVehicle({make:make, model:model, year:year, work:props.work}))
    }

    return(
        <div>
        <label htmlFor="add_vehicle" className="btn no-animation normal-case text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium">Create Vehicle</label>
        <input type="checkbox" id="add_vehicle" className="modal-toggle" />
        <div className="modal">
        <div className="modal-box relative bg-white">
            <label htmlFor="add_vehicle" className="btn btn-sm btn-circle absolute right-2 top-2">✕</label>
            <h3 className="text-lg font-bold text-black">Create Vehicle</h3>
            <form className='m-5 space-y-5' onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="make" className="block mb-2 text-sm font-medium text-gray-900">Enter Vehicle Make</label>
                    <input onChange={(e) => {setMake(e.target.value)}} value={make} type="text" id="make" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Toyota" required/>
                </div>
                <div>
                    <label htmlFor="model" className="block mb-2 text-sm font-medium text-gray-900">Enter Vehicle Model</label>
                    <input onChange={(e) => {setModel(e.target.value)}} value={model} type="text" id="model" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Camry" required/>
                </div>
                <div>
                    <label htmlFor="year" className="block mb-2 text-sm font-medium text-gray-900">Enter Vehicle Year</label>
                    <input onChange={(e) => {setYear(e.target.value)}} value={year} type="number" id="year" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="2018" required/>
                </div>
                <button htmlFor="add_customer" type='submit' className='btn no-animation normal-case text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium'>Submit</button>
            </form>
        </div>
        </div>
        </div> 
    )
}  
