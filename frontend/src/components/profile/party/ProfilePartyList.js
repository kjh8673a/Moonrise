import React from 'react'
import { Route, Routes } from 'react-router-dom'

import ProfilePartyNav from './ProfilePartyNav'
import ProfilePartyParticipateList from './ProfilePartyParticipateList'
import ProfilePartyHostList from './ProfilePartyHostList'

function ProfilePartyList() {
  return (
    <div>
      <ProfilePartyNav />
      <Routes>
        <Route path="part" element={<ProfilePartyParticipateList/>}></Route>
        <Route path="host" element={<ProfilePartyHostList />}></Route>
      </Routes>
    </div>
  )
}

export default ProfilePartyList
