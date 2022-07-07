import { loginService } from "../features/authSlice"
import { useDispatch, useSelector } from "react-redux"
import { useState } from "react";


function Login(){
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const dispatch = useDispatch();
    const { loading, authenticated } = useSelector((store) => store.auth);
    return(
        <div className="h-screen grid place-items-center">
            <div className="p-6 bg-slate-700 rounded-lg shadow-md w-1/2">
                <h1 className=" text-2xl text-white font-bold mb-5">Login</h1>
                <form>
                    <div class="mb-6">
                        <input value={username} onChange={(e) => {setUsername(e.target.value)}} type="text" id="username" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter Username" required/>
                    </div>
                    <div class="mb-6">
                        <input value={password} onChange={(e) => {setPassword(e.target.value)}} type="password" id="password" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter Password" required/>
                    </div>
                    <button type="submit" onSubmit={dispatch(loginService({username:''}))} class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Submit</button>
                </form>
            </div>
        </div> 
     )
}

export default Login
