import { useState } from "react"
import { useDispatch, useSelector } from "react-redux"
import { createJobService } from "../features/jobSlice"

export default function JobModal(){
    const [description, setDescription] = useState('')
    const { token } = useSelector((store) => store.auth)
    const dispatch = useDispatch()

    const handleSubmit = (e) => {
        e.preventDefault();
        const body = {token :token, job:{description:description, complete:false}}
        dispatch(createJobService(body))
    }
    return(
        <div>
            <label for="my-modal" className="btn modal-button">
                <div className="flex flex-row">
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                        <path strokeLinecap="round" strokeLinejoin="round" d="M12 4v16m8-8H4" />
                    </svg>
                    <p className="ml-2 my-auto">Job</p>
                </div>
            </label>
            <input type="checkbox" id="my-modal" class="modal-toggle" />
            <div className="modal">
                <div className="modal-box relative">
                    <label for="my-modal" className="btn btn-sm btn-circle absolute right-2 top-2">✕</label>
                    <h3 class="my-2 text-lg font-bold">Create Job</h3>
                    <form  onSubmit={(e) => {handleSubmit(e)}}>
                        <textarea value={description} onChange={(e) => {setDescription(e.target.value)}} id="message" rows="6" className="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500" placeholder="Enter Description of Job"/>
                        <button type="submit" className="text-white btn btn-success my-2">Create</button>
                    </form>
                </div>
            </div>
        </div>
    )
}