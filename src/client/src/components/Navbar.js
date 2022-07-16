import { useSelector } from "react-redux"
import { useDispatch } from "react-redux";
import { logoutService } from "../features/authSlice";

export default function Navbar(){
   const { token } = useSelector((store) => store.auth);
   const dispatch = useDispatch()

    return(
        <nav className="px-2 py-4 rounded bg-slate-700 absolute top-0 right-0 left-0">
            <div className="container flex flex-wrap justify-between items-center mx-auto">
                <span className="self-center text-xl font-semibold whitespace-nowrap text-white">AutoRepair</span>
                {token ? <button onClick={() => dispatch(logoutService())} type="button" className="text-white focus:ring-4 font-medium rounded-lg text-sm px-5 py-2 bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-blue-800">Logout</button> : null}
            </div>
        </nav>
    )
}