import axios from 'axios'
import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { setPartyDetail } from '../../../feature/reducer/PartyReducer'

import PartyCandidate from './PartyCandidate'
import PartyComment from './PartyComment'
import PartyDetailCard from './PartyDetailCard'
import PartyEnroll from './PartyEnroll'

function PartyDetail() {
  const movePage = useNavigate();

  function goBefore(){
    movePage('/community/list/party');
  }

  const partyId = useSelector(state => state.party.partyId);
  const dispatch = useDispatch(); 
  useEffect(() => {
    axios.get('http://i8b310.p.ssafy.io:9001/party/read/'+partyId)
      .then(response => {
        dispatch(setPartyDetail(response.data.findParty));
      });
  }, );
  return (
    <div className='PartyDetail mx-60 py-10 grid grid-cols-2 gap-4'>
      <div className="col-span-1">
        <button className='text-white' onClick={goBefore}> &lt; 이전으로 </button>
        <p className='movieName text-orange-600 mt-4'>해리포터와 마법사의 돌</p>
        <p className="partyTitle text-2xl text-white">{useSelector(state => state.party.partyDetail.title)}</p>
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
