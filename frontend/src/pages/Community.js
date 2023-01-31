
import React from 'react'
import { Routes, Route } from 'react-router-dom'
import CommunityDetail from '../components/community/CommunityDetail';
import CommunityList from '../components/community/CommunityList';

function Community() {
  return (
    <div className='bg-community bg-cover px-8'>
        <Routes>
            <Route path="list/*" element={<CommunityList/>}></Route>
            <Route path="detail/*" element={<CommunityDetail/>}></Route>
          </Routes>
    </div>
  )
}

export default Community
