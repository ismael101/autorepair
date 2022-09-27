import { useState } from "react"
import { useDispatch } from "react-redux"

export default function AddPart(){
    const [title, setTitle] = useState("")
    const [location, setLocation] = useState("")
    const [cost, setCost] = useState()
    const [ordered, setOrdered] = useState(false)
    return(
        <div>
        <label htmlFor="add_labor" className="btn no-animation normal-case text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium">Create Part</label>
        <input type="checkbox" id="add_labor" className="modal-toggle" />
        <div className="modal">
        <div className="modal-box relative bg-white">
            <label htmlFor="add_labor" className="btn btn-sm btn-circle absolute right-2 top-2">✕</label>
            <h3 className="text-lg font-bold text-black">Create Part</h3>
            <form className='m-5 space-y-5' onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="title" className="block mb-2 text-sm font-medium text-gray-900">Enter Title</label>
                    <input onChange={(e) => {setTitle(e.target.value)}} value={title} type="text" id="title" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Broken Tail Light" required/>
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
                    <label htmlFor="ordered" className="block mb-2 text-sm font-medium text-gray-900">Set Ordered</label>
                    <input checked={ordered} onChange={() => {setOrdered(!ordered)}} type="checkbox" className={`${ordered ? "bg-green-600 ":"bg-red-600"} toggle`}/>
                </div>
                <button htmlFor="add_labor" type='submit' className='btn no-animation normal-case text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium'>Submit</button>
            </form>
        </div>
        </div>
        </div>
    )
}