import { useEffect, useState } from "react"
import { useSelector, useDispatch } from 'react-redux'
import { useNavigate, Link } from 'react-router-dom'
import { reset, signup } from '../features/auth/authSlice'
import Spinner from "../components/Spinner"

export default function Signup(){
    const [ username, setUsername ] = useState("")
    const [ password, setPassword ] = useState("")
    const dispatch = useDispatch()
    const navigate = useNavigate()

    const { token, loading, error, message} = useSelector(
        (state) => state.auth
    )

    useEffect(() => {
        if(token){
            navigate('/works')
        }

    },[token, error, navigate, dispatch])

    const handleSubmit = () => {
        const userData = {
            username:username,
            password:password
        }
        setUsername("")
        setPassword("")
        dispatch(signup(userData))
    }
    


    if(loading){
        return(
            <Spinner/>
        )
    }

    return(
        <div className="h-screen bg-gray-300 flex">
            <div className="rounded-sm w-1/3 px-10 pt-10 pb-2 bg-white mx-auto mt-36 h-1/2">
                <form className="space-y-5" onSubmit={handleSubmit}>
                    <h1 className="text-black text-xl font-bold">Create your new account</h1>
                    <div>
                        <label htmlFor="username" className="block mb-2 text-sm font-medium text-gray-900">Your Username</label>
                        <input onChange={(e) => {setUsername(e.target.value)}} value={username} type="text" id="username" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="ismael101" required/>
                    </div>
                    <div>
                        <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-900">Your Password</label>
                        <input onChange={(e) => {setPassword(e.target.value)}} value={password} type="password" id="password" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="••••••••"required/>
                    </div>
                    <button type="submit" className="btn no-animation text-white normal-case bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium block w-full text-center">Submit</button>
                </form>
                <p className="mt-5 text-gray-700">Already have an account? <Link to="/"><span className="text-blue-600 hover:underline">Login</span></Link></p>
            </div>
            {error && <div className="alert alert-error shadow-lg flex absolute bottom-4 right-4 w-1/3 text-white">
                            <div className="flex">
                                <svg xmlns="http://www.w3.org/2000/svg" className="stroke-current flex-shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" /></svg>
                                <span>Error! {error.error}</span>
                            </div>
                            <button onClick={() => {dispatch(reset())}} type="button" className="ml-auto -mx-1.5 -my-1.5 bg-red-100 text-red-500 rounded-lg focus:ring-2 focus:ring-red-400 p-1.5 hover:bg-red-200 inline-flex h-8 w-8">
                                <span className="sr-only">Close</span>
                                <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fillRule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clipRule="evenodd"></path></svg>
                            </button>
                        </div>
            }
            {message && <div className="alert alert-success shadow-lg flex absolute bottom-4 right-4 w-1/3 text-white">
                        <div className="flex">
                            <svg xmlns="http://www.w3.org/2000/svg" className="stroke-current flex-shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" /></svg>
                            <span>Success! {message}</span>
                        </div>
                        <button onClick={() => {dispatch(reset())}} type="button" className="ml-auto -mx-1.5 -my-1.5 bg-green-100 text-green-500 rounded-lg focus:ring-2 focus:ring-green-400 p-1.5 hover:bg-green-200 inline-flex h-8 w-8">
                            <span className="sr-only">Close</span>
                            <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fillRule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clipRule="evenodd"></path></svg>
                        </button>
                    </div>
            }
        </div>
    )
}