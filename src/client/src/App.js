import { BrowserRouter as Router, Routes, Route} from 'react-router-dom'
import { useSelector } from 'react-redux';
import Sidebar from './components/Sidebar'
import Login from './pages/Login';
import Signup from './pages/Signup';
import Dashboard from './pages/Dashboard'
import Works from './pages/Works'
import Work from './pages/Work'
import Customers from './pages/Customers'
import Vehicles from './pages/Vehicles'
import Insurances from './pages/Insurances'
import Parts from './pages/Parts'
import Labors from './pages/Labors'
import Navbar from './components/Navbar';


function App() {
  const { token } = useSelector(
    (state) => state.auth
  )
  return (
    <div className={`${token && "flex"} App h-auto bg-gray-300`}>
        <Router>
          <Sidebar/>
          <div className='w-full'>
            <Navbar/>
            <Routes>
              <Route path='/' element={<Login/>} />
              <Route path='/signup' element={<Signup/>}/>
              <Route path='/dashboard' element={<Dashboard/>}/>
              <Route path='/works' element={<Works/>}/>
              <Route path='/works/:id' element={<Work/>}/>
              <Route path='/customers' element={<Customers/>} />
              <Route path='/vehicles' element={<Vehicles/>}/>
              <Route path='/insurances' element={<Insurances/>}/>
              <Route path='/parts' element={<Parts/>}/>
              <Route path='/labors' element={<Labors/>}/>
            </Routes>
          </div>
        </Router>
    </div>
  );
}

export default App;
