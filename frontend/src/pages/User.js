import React from 'react'
import { Route, Routes } from 'react-router-dom';
import UserLogin from '../components/user/UserLogin';
import UserRegister from '../components/user/UserRegister';
function User() {
  return (
    <div className='bg-main h-screen bg-cover'>
        <Routes>
            <Route path="/*" element={<UserLogin/>}></Route>
            <Route path="register/*" element={<UserRegister/>}></Route>
        </Routes>
        
    </div>
  )
}

export default User