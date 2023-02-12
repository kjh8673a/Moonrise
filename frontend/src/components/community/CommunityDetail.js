import React from 'react'
import { Route, Routes } from 'react-router-dom'
import PartyDetail from './party/PartyDetail'
import BoardDetail from './board/BoardDetail'

function CommunityDetail() {
  return (
    <div className='communityDetail'>
        <Routes>
            <Route path="party/:partyId" element={<PartyDetail/>}></Route>
            <Route path="board/*" element={<BoardDetail/>}></Route>
          </Routes>
    </div>
  )
}

export default CommunityDetail