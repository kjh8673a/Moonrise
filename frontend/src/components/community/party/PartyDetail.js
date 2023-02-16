import React from 'react'
import { useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'

import PartyCandidate from './PartyCandidate'
import PartyComment from './PartyComment'
import PartyDetailCard from './PartyDetailCard'
import PartyEnroll from './PartyEnroll'

function PartyDetail() {
  const movePage = useNavigate();

  function goBefore(){
    movePage('/community/list/party');
  }

  
  return (
    <div className='grid grid-cols-2 gap-4 py-10 PartyDetail mx-60'>
      <div className="col-span-1">
        <button className='text-white' onClick={goBefore}> &lt; 이전으로 </button>
        <p className='mt-4 text-orange-600 movieName'>{useSelector(state => state.movie.movieTitle)}</p>
        <p className="text-2xl text-white partyTitle">{useSelector(state => state.party.partyDetail.title)}</p>
        <PartyDetailCard/>
      </div>
      <div className="col-span-1">
        <PartyComment/>
        <PartyEnroll/>
        <PartyCandidate/>
      </div>
    </div>
  )
}

export default PartyDetail
