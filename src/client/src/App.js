import {BrowserRouter, Routes, Route} from 'react-router-dom'
import Login from './pages/Login';
import Jobs from './pages/Jobs'
import Job from './pages/Job'
import Navbar from './components/Navbar';


export default function App() {
  return (
    <div className="App row h-screen">
      <header>
        <Navbar/>
      </header>
      <section>
        <BrowserRouter>
          <Routes>
            <Route path='/' element={<Login/>}/>
            <Route path='/jobs' element={<Jobs/>}/>
            <Route path='/jobs/:id' element={<Job/>} />
          </Routes>
        </BrowserRouter>
      </section>
    </div>
  );
}