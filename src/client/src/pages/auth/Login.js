import { useEffect, useState } from "react"
import { useSelector, useDispatch } from 'react-redux'
import { Link, useNavigate } from 'react-router-dom'
import { login, reset } from '../../features/auth/authSlice'

export default function Login(){
    const [ username, setUsername ] = useState("ismael101")
    const [ password, setPassword ] = useState("password101")
    const [ error, setError ] = useState(false)
    const dispatch = useDispatch()
    const navigate = useNavigate()

    const { token, isLoading, isError, isSuccess, message } = useSelector(
        (state) => state.auth
    )

    useEffect(() => {
        if(isError){
            setError(true)
        }
        if(token){
            navigate('/works')
        }
    },[token, isError, isSuccess, navigate, dispatch])

    const handleSubmit = (e) => {
        e.preventDefault()
        const userData = {
            username:username,
            password:password
        }
        dispatch(login(userData))
    }
    

    if(isLoading){
        return(
            <div className="h-screen bg-gray-300 grid place-items-center">
                <div role="status">
                    <svg aria-hidden="true" className="mr-2 w-8 h-8 text-gray-200 animate-spin dark:text-gray-600 fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
                        <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
                    </svg>
                </div>
            </div>
        )
    }
    
    return(
        <div className="h-screen bg-gray-300 flex">
            <div className="rounded-sm w-1/3 px-10 pt-10 h-1/2 mt-36 pb-2 bg-white mx-auto">
                <form className="space-y-5" onSubmit={handleSubmit}>
                    <h1 className="text-black text-xl font-bold">Login to your account</h1>
                    <div>
                        <label htmlFor="username" className="block mb-2 text-sm font-medium text-gray-900">Your Username</label>
                        <input onChange={(e) => {setUsername(e.target.value)}} value={username} type="text" id="username" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="ismael101" required/>
                    </div>
                    <div>
                        <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-900">Your Password</label>
                        <input onChange={(e) => {setPassword(e.target.value)}} value={password} type="password" id="password" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="password123" required/>
                    </div>
                    <button type="submit" className="btn no-animation normal-case text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium block w-full text-center">Submit</button>
                </form>
                <p className="mt-5 text-gray-700">Dont have an account yet? <Link to="/signup"><span className="text-blue-600 hover:underline">Signup</span></Link></p>
            </div>
                    {error && <div className="alert alert-error shadow-lg flex absolute bottom-4 right-4 w-1/3 text-white">
                            <div className="flex">
                                <svg xmlns="http://www.w3.org/2000/svg" className="stroke-current flex-shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" /></svg>
                                <span>Error! {message}</span>
                            </div>
                            <button onClick={() => {setError(false)}} type="button" className="ml-auto -mx-1.5 -my-1.5 bg-red-100 text-red-500 rounded-lg focus:ring-2 focus:ring-red-400 p-1.5 hover:bg-red-200 inline-flex h-8 w-8">
                                <span className="sr-only">Close</span>
                                <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fillRule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clipRule="evenodd"></path></svg>
                            </button>
                        </div>
                    }
        </div>
    )
}