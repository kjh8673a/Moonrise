
import React, { Component } from 'react'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import CommunityNav from '../components/community/CommunityNav'
import PartyList from '../components/community/party/PartyList'
import MovieDetail from '../components/community/movie/MovieDetail';
import BoardList from '../components/community/board/BoardList';

export default class Community extends Component {
  render() {
    return (
      <div className="bg-community bg-cover px-8">
        <div className="grid grid-cols-3 gap-4">
          <div className='col-span-1'>
            <MovieDetail/>
          </div>
          <div className='col-span-2'>
            <CommunityNav/>
            <BrowserRouter>
              <Routes>
                <Route path="/board" element={<BoardList/>}></Route>
                <Route path="/party" element={<PartyList/>}></Route>
              </Routes>
            </BrowserRouter>
          </div>
        </div>
      </div>
    )
  }
}