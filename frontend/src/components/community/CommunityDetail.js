import React from 'react'
import { Route, Routes } from 'react-router-dom'
import TalkDetail from './talk/TalkDetail'
import PartyDetail from './party/PartyDetail'
import BoardDetail from './board/BoardDetail'

function CommunityDetail() {
  return (
    <div className='communityDetail'>
        <Routes>
            {/* <Route path="board" element={<BoardDetail/>}></Route> */}
            {/* <Route path="board" element={<TalkDetail/>}></Route> */}
            <Route path="party" element={<PartyDetail/>}></Route>
            <Route path="talk" element={<TalkDetail/>}></Route>
            <Route path="board" element={<BoardDetail/>}></Route>
          </Routes>
    </div>
  )
}

export default CommunityDetail