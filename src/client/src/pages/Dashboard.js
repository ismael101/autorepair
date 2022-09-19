import { useEffect, useState } from 'react'
import { useSelector } from 'react-redux'

export default function Dashboard(){
    const [show, setShow] = useState(false)
    
    useEffect(() => {
        
    },[])

    return(
        <div className="h-screen bg-gray-300 p-20">
            <div className="flex flex-row justify-between border-b-2 py-2">
                <div>
                    <h1 className="text-black text-3xl font-bold">Work Orders</h1>
                </div>
                <div>
                    <label htmlFor="my-modal" className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 block w-full text-center">Add Work Order</label>
                    <input type="checkbox" id="my-modal" className="modal-toggle" />
                    <div className="modal">
                        <div className="modal-box">
                            <h3 className="font-bold text-lg">Congratulations random Internet user!</h3>
                            <p className="py-4">You've been selected for a chance to get one year of subscription to use Wikipedia for free!</p>
                            <div className="modal-action">
                            <label htmlFor="my-modal" className="btn">Yay!</label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )

}