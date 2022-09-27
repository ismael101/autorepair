import { useDispatch } from "react-redux";
import { deleteWork } from "../../../features/work/workSlice";

export default function DeleteWork(props){
    const dispatch = useDispatch()

    const handleDelete = () => {
        dispatch(deleteWork(props.work.id))
    }

    return(
        <div>
        <label htmlFor="delete_work" className="btn no-animation normal-case text-white bg-purple-700 hover:bg-purple-800 focus:ring-4 focus:outline-none focus:ring-purple-300 font-medium">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-6 h-6">
                <path strokeLinecap="round" strokeLinejoin="round" d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0" />
            </svg>
        </label>
        <input type="checkbox" id="delete_work" className="modal-toggle" />
        <div className="modal">
        <div className="modal-box relative bg-gray-800 flex">
            <label htmlFor="delete_work" className="btn btn-sm btn-circle absolute right-2 top-2">✕</label>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="text-center text-white mx-5 w-15 h-15">
                <path strokeLinecap="round" strokeLinejoin="round" d="M12 9v3.75m9-.75a9 9 0 11-18 0 9 9 0 0118 0zm-9 3.75h.008v.008H12v-.008z" />
            </svg>
            <h3 className="text-center text-lg font-bold text-white">Are You Sure You Want To Delete Work?</h3>
            <div className="mx-auto flex flex-row justify-center space-x-2">
                <button htmlFor="delete_work" onClick={() => handleDelete()} type="button" className="btn no-animation normal-case text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 font-medium">Delete</button>
                <button htmlFor="delete_work" type="button" className="btn no-animation normal-case text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium">Cancel</button>
            </div>
        </div>
        </div>
        </div>  
    )
}