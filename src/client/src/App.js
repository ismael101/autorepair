import { BrowserRouter as Router, Routes, Route} from 'react-router-dom'
import { useState } from 'react';
import { useSelector } from 'react-redux';
import Login from './pages/Login.js'
import Signup from './pages/Signup.js'
import Dashboard from './pages/Works.js';
import Navbar from './components/Navbar.js';

function App() {
  const { token } = useSelector(
    (state) => state.auth
  )
  const [open, setOpen] = useState(false);
  return (
    <div className="App h-screen bg-gray-300 flex">
        <Router>
          <Navbar/>
          <Routes>
            <Route path='/' element={<Login/>} />
            <Route path='/signup' element={<Signup/>}/>
            <Route path='/works' element={<Dashboard/>}/>
          </Routes>
        </Router>
    </div>
  );
}

export default App;
