import { useSelector } from "react-redux"
import { useDispatch } from "react-redux";
import { logoutService } from "../features/authSlice";

export default function Navbar(){
   const { token } = useSelector((store) => store.auth);
   const dispatch = useDispatch()

    return(
        <nav className="px-2 py-4 absolute top-0 right-0 left-0">
            <div className="container flex flex-wrap justify-between items-center mx-auto">
                <span className="self-center text-xl font-semibold whitespace-nowrap text-black">AutoRepair</span>
                {token ? <button onClick={() => {dispatch(logoutService())}} type="button" class="border focus:outline-none focus:ring-4 font-medium rounded-lg text-sm px-5 py-2.5 bg-gray-800 text-white border-gray-600 hover:bg-gray-700 hover:border-gray-600 focus:ring-gray-700">Logout</button> : null}
            </div>
        </nav>
    )
}