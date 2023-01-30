
import React, { Component } from 'react'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import CommunityNav from '../components/community/CommunityNav'
import PartyList from '../components/community/party/PartyList'
import MovieDetail from '../components/community/movie/MovieDetail';
import BoardList from '../components/community/board/BoardList';

export default class Community extends Component {
  render() {
    return (
      <div>
        {/* <MovieDetail />
        <BoardList /> */}
        <BrowserRouter>
        <CommunityNav/>
        <Routes>
          <Route path="/party" element={<PartyList/>}></Route>
        </Routes>
        </BrowserRouter>

      </div>
    )
  }
}