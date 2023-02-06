import React from 'react'
import { Route, Routes } from 'react-router-dom'
import ProfileBoardMyList from './ProfileBoardMyList'
import ProfileBoardBookmarkList from './ProfileBoardBookmarkList'
import ProfileBoardNav from './ProfileBoardNav'

function ProfileBoardList() {
  return (
    <div>
      <ProfileBoardNav />
      <Routes>
        <Route path="bookmark" element={<ProfileBoardBookmarkList />}></Route>
        <Route path="my" element={<ProfileBoardMyList />}></Route>
      </Routes>
    </div>
  )
}

export default ProfileBoardList
