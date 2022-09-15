import { BrowserRouter as Router, Routes, Route, BrowserRouter} from 'react-router-dom'
import Login from './pages/Login.js'
import Signup from './pages/Signup.js'
import Navbar from './components/Navbar.js';

function App() {
  return (
    <div className="App h-screen">
      <Navbar/>
      <Router>
        <Routes>
          <Route path='/' element={<Login/>} />
          <Route path='/signup' element={<Signup/>}></Route>
        </Routes>
      </Router>
    </div>

  );
}

export default App;
