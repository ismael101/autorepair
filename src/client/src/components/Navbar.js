import { useSelector, useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { logout } from '../features/auth/authSlice'

export default function Navbar(){
    const dispatch = useDispatch()
    const navigate = useNavigate()
    const { token } = useSelector(
        (state) => state.auth
    )

    const signout = async () => {
        await dispatch(logout())
        navigate('/')
    }

    return(
        <nav className="bg-gray-300 px-2 sm:px-4 py-2.5 w-full z-20">
        <div className="container flex flex-wrap justify-between items-center mx-auto">
            <div className="flex items-center">
                <span className="text-black self-center text-xl font-semibold whitespace-nowrap">Autorepair</span>
            </div>
            <div className="flex md:order-2">
                { token ? <button onClick={() => signout()} type="button" className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-3 md:mr-0">Logout</button> : null}
            </div>
        </div>
    </nav>
    )

}