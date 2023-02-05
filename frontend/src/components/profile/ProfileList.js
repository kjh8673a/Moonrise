import React from 'react'
import { Route, Routes } from 'react-router-dom'
import MainNav from '../common/MainNav'
import ProfileCard from './profile/ProfileCard'
import ProfileNav from './ProfileNav'
import ProfileLogList from './log/ProfileLogList';
import ProfileBoardList from './board/ProfileBoardList';
import ProfilePartyList from './party/ProfilePartyList';

function ProfileList() {
  return (
    <div>
      <MainNav/>
      <div className='grid grid-rows-4 h-[85vh]'>
        <div className='row-span-1 '>
            
        </div>
        <div className='grid grid-cols-3 row-span-3 bg-[#315B4C] bg-opacity-80'>
            <div className='col-span-1'></div>
            <div className='col-span-2 p-5'>
              <ProfileNav />
                <Routes>
                  <Route path="" element={<ProfileLogList />}></Route>
                  <Route path="board" element={<ProfileBoardList />}></Route>
                  <Route path="party" element={<ProfilePartyList />}></Route>
                </Routes>
            </div>
        </div>
      </div>
      <div className='fixed w-1/5 bg-white top-60 left-40 h-2/3'>
        <ProfileCard />
      </div>
    </div>
  )
}

export default ProfileList
