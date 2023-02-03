import React from 'react'
import Profile from '../../../assets/img/profile.png'

function PartyCandidateCard(props) {
  return (
    <div className='PartyCandidateCard bg-green-700 p-4 rounded-lg col-span-2 text-center'>
        <img className='rounded-full h-20 w-20' src={Profile} alt="참가 신청 목록 프로필 이미지" />
        <p className='mt-4'>{props.name}</p>
    </div>
  )
}

export default PartyCandidateCard