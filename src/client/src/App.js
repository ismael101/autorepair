import { BrowserRouter as Router, Routes, Route} from 'react-router-dom'
import { useState } from 'react';
import { useSelector } from 'react-redux';
import Login from './pages/Login.js'
import Signup from './pages/Signup.js'
import Dashboard from './pages/Dashboard.js';
import Navbar from './components/Navbar.js';
import Sidebar from './components/Sidebar.js';

function App() {
  const { token } = useSelector(
    (state) => state.auth
  )
  const [open, setOpen] = useState(false);
  return (
    <div className={`${token && "flex"} App h-screen bg-gray-300`}>
        <Router>
          <Sidebar/>
          <div className='w-full'>
            <Navbar/>
            <Routes>
              <Route path='/' element={<Login/>} />
              <Route path='/signup' element={<Signup/>}/>
              <Route path='/dashboard' element={<Dashboard/>}/>
            </Routes>
          </div>
        </Router>
    </div>
  );
}

export default App;
