import { BrowserRouter as Router, Routes, Route} from 'react-router-dom'
import { useSelector } from 'react-redux';
import Login from './pages/auth/Login.js'
import Signup from './pages/auth/Signup.js'
import Dashboard from './pages/info/Dashboard.js';
import Navbar from './components/navigation/Navbar.js';
import Works from './pages/list/Works'
import Work from './pages/item/Work.js';
import Sidebar from './components/navigation/Sidebar.js';

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
            </Routes>
          </div>
        </Router>
    </div>
  );
}

export default App;
