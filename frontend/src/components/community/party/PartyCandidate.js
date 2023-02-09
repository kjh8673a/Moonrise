import React, { useEffect, useState } from 'react'
import PartyCandidateCard from './PartyCandidateCard';

function PartyCandidate(props) {
  const [partyJoins, setPartyJoins] = useState([]);
  useEffect(() => {
    setPartyJoins(props.partyJoins)
    return () => {
    }
  }, [props.partyJoins])
  if(props.type === "승인"){
    return (
      <div className='PartyCandidate'>
        <p className='mt-3 text-lg text-white'>참여 확정</p>
        <div className='grid grid-cols-8 gap-4 mt-3'>
          {partyJoins.map((partyJoin, idx) => (
            <PartyCandidateCard key={idx} partyJoin={partyJoin} type={props.type} setIsCommentChange={props.setIsCommentChange} isCommentChange={props.isCommentChange}/>
          ))}
        </div>
      </div>
    )
  }
  else{
    return (
      <div className='PartyCandidate'>
        <p className='mt-3 text-lg text-white'>저희도 참여하고 싶어요!</p>
        <div className='grid grid-cols-8 gap-4 mt-3'>
          {partyJoins.map((partyJoin, idx) => (
            <PartyCandidateCard key={idx} partyJoin={partyJoin} type={props.type} setIsCommentChange={props.setIsCommentChange} isCommentChange={props.isCommentChange}/>
          ))}
        </div>
      </div>
    )
  }
}

export default PartyCandidate