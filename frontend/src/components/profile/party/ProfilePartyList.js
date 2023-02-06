import React from 'react'
import { Route, Routes } from 'react-router-dom'
import ProfilePartyHost from './ProfilePartyHost'
import ProfilePartyNav from './ProfilePartyNav'
import ProfilePartyParticipate from './ProfilePartyParticipate'

function ProfilePartyList() {
  return (
    <div>
      <ProfilePartyNav />
      <Routes>
        <Route path="part" element={<ProfilePartyParticipate/>}></Route>
        <Route path="host" element={<ProfilePartyHost />}></Route>
      </Routes>
    </div>
  )
}

export default ProfilePartyList
