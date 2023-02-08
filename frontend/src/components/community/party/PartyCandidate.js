import React from 'react'
import PartyCandidateCard from './PartyCandidateCard';

const candidateData = ["최현인", "정상민", "박윤지", "김동률", "조원희", "권지훈"];
function PartyCandidate() {
  const candidateList = candidateData.map((name, idx) => (
    <PartyCandidateCard name={name} key={idx}/>
  ))
  return (
    <div className='PartyCandidate'>
        <p className='mt-3 text-lg text-white'>참가 신청 목록</p>
        <div className='grid grid-cols-8 gap-4 mt-3'>{candidateList}</div>
    </div>
  )
}

export default PartyCandidate