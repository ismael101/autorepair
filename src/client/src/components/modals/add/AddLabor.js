import { useState } from "react"
import { useDispatch } from "react-redux"
import { createLabor } from "../../../features/labor/laborSlice"

export default function AddLabor(props){
    const dispatch = useDispatch()
    const [task, setTask] = useState("")
    const [location, setLocation] = useState("")
    const [cost, setCost] = useState()
    const [complete, setComplete] = useState()

    const handleSubmit = () => {
        dispatch(createLabor({task:task, location:location, cost:cost, complete:complete, work:props.work}))
    }

    return(
        <div>
        <label htmlFor="add_labor" className="btn no-animation normal-case text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium">Create Labor</label>
        <input type="checkbox" id="add_labor" className="modal-toggle" />
        <div className="modal">
        <div className="modal-box relative bg-white">
            <label htmlFor="add_labor" className="btn btn-sm btn-circle absolute right-2 top-2">✕</label>
            <h3 className="text-lg font-bold text-black">Create Labor</h3>
            <form className='m-5 space-y-5' onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="task" className="block mb-2 text-sm font-medium text-gray-900">Enter Task</label>
                    <input onChange={(e) => {setTask(e.target.value)}} value={task} type="text" id="task" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Broken Tail Light" required/>
                </div>
                <div>
                    <label htmlFor="location" className="block mb-2 text-sm font-medium text-gray-900">Enter Location</label>
                    <input onChange={(e) => {setLocation(e.target.value)}} value={location} type="text" id="location" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Broken Tail Light" required/>
                </div>
                <div>
                    <label htmlFor="cost" className="block mb-2 text-sm font-medium text-gray-900">Enter Cost</label>
                    <input onChange={(e) => {setCost(e.target.value)}} value={cost} type="number" id="cost" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Broken Tail Light" required/>
                </div>
                <div>
                    <label htmlFor="complete" className="block mb-2 text-sm font-medium text-gray-900">Set Status</label>
                    <input checked={complete} onChange={() => {setComplete(!complete)}} type="checkbox" className={`${complete ? "bg-green-600 ":"bg-red-600"} toggle`}/>
                </div>
                <button htmlFor="add_labor" type='submit' className='btn no-animation normal-case text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium'>Submit</button>
            </form>
        </div>
        </div>
        </div>
    )
}