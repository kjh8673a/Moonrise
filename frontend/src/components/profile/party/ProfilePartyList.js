import React from 'react'
import { Route, Routes } from 'react-router-dom'

import ProfilePartyNav from './ProfilePartyNav'
import ProfilePartyParticipateList from './ProfilePartyParticipateList'
import ProfilePartyHostList from './ProfilePartyHostList'

function ProfilePartyList(props) {
  const showTopButton = () => {
    props.showTopButton();
  };
  return (
    <div>
      <ProfilePartyNav />
      <Routes>
        <Route path="part" element={<ProfilePartyParticipateList showTopButton={showTopButton}/>}></Route>
        <Route path="host" element={<ProfilePartyHostList showTopButton={showTopButton}/>}></Route>
      </Routes>
    </div>
  )
}

export default ProfilePartyList
