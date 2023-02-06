import React from 'react';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Main from './pages/Main';
import Community from './pages/Community';
import Search from './pages/Search';
import User from './pages/User';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
          <Routes>
              <Route path={"/"} element={<Main />}></Route>
              <Route path={"/user/*"} element={<User />}></Route>
              <Route path={"/search"} element={<Search />}></Route>
              <Route path={"/community/*"} element={<Community />}></Route>
          </Routes>
        </BrowserRouter>
    </div>
  );
}

export default App;
