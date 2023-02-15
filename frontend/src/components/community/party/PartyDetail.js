import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import PartyCandidate from './PartyCandidate'
import PartyComment from './PartyComment'
import PartyDetailCard from './PartyDetailCard'
import PartyEnroll from './PartyEnroll'

function PartyDetail() {
  const access_token = useSelector(state => state.member.accessToken);
  const [partyDetail, setPartyDetail] = useState({});
  const [partyComments, setPartyComments] = useState([]);
  const [isCommentChange, setIsCommentChange] = useState(true);
  const [partyJoinWait, setPartyJoinWait] = useState([])
  const [partyJoinAccept, setPartyJoinAccept] = useState([])
  const [isWriter, setIsWriter] = useState();
  const [joinStatus, setJoinStatus] = useState();
  const partyId = useParams().partyId;
  const movePage = useNavigate();
  const [date, setDate] = useState("");
  
  function goBefore(){
    movePage('/community/list/party');
  }
  useEffect(() => {
    const baseURL = process.env.REACT_APP_BASE_URL;
    const config = { 
      headers: {
        "access_token": access_token,
      }
    }
    axios.get(baseURL+ '/api/party/read/'+partyId, config)
        .then(response => {
          setPartyDetail(response.data.data.findParty);
          setPartyComments(response.data.data.findParty.partyComments);
          setIsWriter(response.data.data.isWriter);
          setJoinStatus(response.data.data.joinStatus);
          setPartyJoinWait(response.data.data.findParty.partyJoinWait);
          setPartyJoinAccept(response.data.data.findParty.partyJoinAccept);
          setDate(response.data.data.findParty.partyDate.split("T")[0].split("-").join("."));
        });
    return () => {
    }
    }, [access_token, partyId, isWriter, isCommentChange])
  return (
    <div className='h-screen'>
      <div className='relative flex pt-4 mx-60'>
        <button className='absolute bottom-0 left-0 pb-2 text-white' onClick={goBefore}> &lt; 이전으로 </button>
        <p className='flex-1 pb-2 text-2xl text-center text-white border-b-2 border-white border-opacity-50'>뒷풀이 정보</p>
      </div>
      <div className='grid grid-cols-2 gap-4 PartyDetail mx-60'>
        <div className="col-span-1">
          <div className='flex mt-2'>
            <p className="flex-1 text-lg text-white partyTitle">{partyDetail.title}</p>
            <p className='mt-1 text-right text-dal-orange movieName'>{useSelector(state => state.movie.movieTitle)}</p>\
          </div>
          <PartyDetailCard partyDetail={partyDetail} date={date}/>
          <PartyCandidate partyId={partyId} partyJoins={partyJoinAccept} type={"승인"} setIsCommentChange={setIsCommentChange} isCommentChange={isCommentChange}/>
        </div>
        <div className="col-span-1">
          <PartyComment setIsCommentChange={setIsCommentChange} isCommentChange={isCommentChange} partyComments={partyComments} partyId={partyId}/>
          {!isWriter && (<PartyEnroll partyId={partyId} joinStatus={joinStatus} setIsCommentChange={setIsCommentChange} isCommentChange={isCommentChange}/>)}
          {isWriter && (<PartyCandidate partyId={partyId} partyJoins={partyJoinWait} type={"승인대기"} setIsCommentChange={setIsCommentChange} isCommentChange={isCommentChange}/>)}
        </div>
      </div>
    </div>
  )
}

export default PartyDetail
