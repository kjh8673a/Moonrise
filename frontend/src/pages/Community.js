import React, { Component } from 'react'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import CommunityNav from '../components/community/CommunityNav'
import PartyList from '../components/community/party/PartyList'

export default class Community extends Component {
  render() {
    return (
      <div>
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
