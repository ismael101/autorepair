import { useSelector, useDispatch } from 'react-redux'
import { logout } from '../features/auth/authSlice'

export default function Navbar(){
    const dispatch = useDispatch()
    const { token } = useSelector(
        (state) => state.auth
    )
    return(
        <nav className="bg-gray-300 px-2 sm:px-4 py-2.5 dark:bg-gray-900 fixed w-full z-20 top-0 left-0 dark:border-gray-600">
        <div className="container flex flex-wrap justify-between items-center mx-auto">
            <div className="flex items-center">
                <span className="self-center text-xl font-semibold whitespace-nowrap">Autorepair</span>
            </div>
            <div className="flex md:order-2">
                { token ? <button onClick={() => dispatch(logout())} type="button" className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-3 md:mr-0">Logout</button> : null}
            </div>
        </div>
    </nav>
    )

}