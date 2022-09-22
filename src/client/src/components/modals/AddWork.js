import { createWork } from '../../features/work/workSlice'
import { useState } from "react"
import { useDispatch } from "react-redux"

export default function AddWork(){
    const [title, setTitle] = useState("")
    const[description, setDescription] = useState("")
    const[complete, setComplete] = useState(false)
    const dispatch = useDispatch()

    const handleSubmit = () => {
        const work = {title:title, description:description, complete:complete}
        dispatch(createWork(work))
    }

    return(
        <div>
            <label htmlFor="my-modal" className="btn no-animation normal-case text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium">+ Work Order</label>
            <input type="checkbox" id="my-modal" className="modal-toggle" />
            <div className="modal">
            <div className="modal-box relative bg-white">
                <label htmlFor="my-modal" className="btn btn-sm btn-circle absolute right-2 top-2">✕</label>
                <h3 className="text-lg font-bold text-black">Create Work Order</h3>
                <form className='m-5 space-y-5' onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="username" className="block mb-2 text-sm font-medium text-gray-900">Enter Title</label>
                        <input onChange={(e) => {setTitle(e.target.value)}} value={title} type="text" id="username" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Broken Tail Light" required/>
                    </div>
                    <div>
                        <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-900">Enter Description</label>
                        <textarea onChange={(e) => {setDescription(e.target.value)}} rows="4" value={description} type="password" id="password" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="both tail light aren't damaged from the outside but aren't functioning could be due to broken wires" required/>
                    </div>
                    <div>
                        <label htmlFor="complete" className="block mb-2 text-sm font-medium text-gray-900">Set Status</label>
                        <input checked={complete} onChange={() => {setComplete(!complete)}} type="checkbox" className={`${complete ? "bg-green-600 ":"bg-red-600"} toggle`}/>
                    </div>
                    <button htmlFor="my-modal" type='submit' className='btn no-animation normal-case text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium'>Submit</button>
                </form>
            </div>
            </div>
        </div>
    )
}