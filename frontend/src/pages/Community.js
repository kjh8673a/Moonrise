
import React from 'react'
import { Routes, Route } from 'react-router-dom'
import CommunityNav from '../components/community/CommunityNav'
import PartyList from '../components/community/party/PartyList'
import MovieDetail from '../components/community/movie/MovieDetail';
import BoardList from '../components/community/board/BoardList';

function Community() {
  return (
    <div className='community'>
      <div className="bg-community bg-cover px-8">
        <div className="grid grid-cols-3 gap-4">
          <div className='col-span-1'>
            <MovieDetail/>
          </div>
          <div className='col-span-2'>
            <CommunityNav/>
              <Routes>
                <Route path="" element={<BoardList/>}></Route>
                <Route path="party" element={<PartyList/>}></Route>
              </Routes>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Community
