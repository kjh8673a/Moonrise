import React from 'react'
import { Route, Routes } from 'react-router-dom'
import BoardList from './board/BoardList'
import CommunityNav from './CommunityNav'
import CommunityPagination from './CommunityPagination'
import MovieDetail from './movie/MovieDetail'
import TalkList from './talk/TalkList'
import PartyList from './party/PartyList'
import MainNav from '../common/MainNav'

function CommunityList() {
  return (
    <div className='communityList'>
        <MainNav/>
        <div className='grid grid-cols-3 gap-4'>
          <div className='col-span-1'>
            <MovieDetail/>
          </div>
          <div className='col-span-2'>
            <CommunityNav/>
              <Routes>
                <Route path="" element={<BoardList/>}></Route>
                <Route path="talk" element={<TalkList/>}></Route>
                <Route path="party" element={<PartyList/>}></Route>
              </Routes>
            <CommunityPagination total="20" limit="2"/>
          </div>       
        </div>
    </div>
  )
}

export default CommunityList