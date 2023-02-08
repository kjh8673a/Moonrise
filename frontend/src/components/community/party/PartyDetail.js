import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import PartyCandidate from './PartyCandidate'
import PartyComment from './PartyComment'
import PartyDetailCard from './PartyDetailCard'
import PartyEnroll from './PartyEnroll'

function PartyDetail() {
  const param = useParams()
  const access_token = useSelector(state => state.member.accessToken);
  const [partyDetail, setPartyDetail] = useState({});
  
  const movePage = useNavigate();
  function goBefore(){
    movePage('/community/list/party');
  }
  useEffect(() => {
    const baseURL = process.env.REACT_APP_BASE_URL;
    const partyId = param.partyId;
    const config = { 
      headers: {
        "access_token": access_token,
      }
    }
    axios.get(baseURL+ '/api/party/read/'+partyId, config)
        .then(response => {
          setPartyDetail(response.data.data.findParty);
          console.log(response.data.data)
        });
      return () => {
      }
    }, [access_token, param.partyId])

  return (
    <div className='grid grid-cols-2 gap-4 py-10 PartyDetail mx-60'>
      <div className="col-span-1">
        <button className='text-white' onClick={goBefore}> &lt; 이전으로 </button>
        <p className='mt-4 text-orange-600 movieName'>{useSelector(state => state.movie.movieTitle)}</p>
        <p className="text-2xl text-white partyTitle">{partyDetail.title}</p>
        <PartyDetailCard partyDetail={partyDetail}/>
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
