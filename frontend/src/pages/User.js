import React from 'react'
import { Route, Routes } from 'react-router-dom';
import UserKakaoLogin from '../components/user/UserKakaoLogin';
import UserLogin from '../components/user/UserLogin';
import UserRegister from '../components/user/UserRegister';
function User() {
  return (
    <div className='h-screen bg-cover bg-main'>
        <Routes>
            <Route path="/*" element={<UserLogin/>}></Route>
            <Route path="register/*" element={<UserRegister/>}></Route>
            <Route path="kakaoLogin" element={<UserKakaoLogin/>}></Route>
        </Routes>
        
    </div>
  )
}

export default User