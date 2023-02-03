import React from 'react'
import PartyCandidateCard from './PartyCandidateCard';

const candidateData = ["최현인", "정상민", "박윤지", "김동률", "조원희", "권지훈"];
function PartyCandidate() {
  const candidateList = candidateData.map((name) => (
    <PartyCandidateCard name={name}/>
  ))
  return (
    <div className='PartyCandidate'>
        <p className='text-lg text-white mt-3'>참가 신청 목록</p>
        <div className='grid grid-cols-8 gap-4 mt-3'>{candidateList}</div>
    </div>
  )
}

export default PartyCandidate