import {BrowserRouter, Routes, Route} from 'react-router-dom'
import Login from './pages/Login';


export default function App() {
  return (
    <div className="App row h-screen bg-slate-800">
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<Login/>}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

