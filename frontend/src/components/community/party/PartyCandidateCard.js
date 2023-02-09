import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import Profile from '../../../assets/img/profile.png'

function PartyCandidateCard(props) {
  const [partyJoin, setPartyJoin] = useState({});
  const [onModal, setOnModal] =useState(false);
  const access_token = useSelector(state => state.member.accessToken);
  const baseURL = process.env.REACT_APP_BASE_URL;
  const config = { 
    headers: {
      "access_token": access_token,
    }
  };
  const joinStatusChange = (event) => {
    axios.get(baseURL + "/api/party/join/status?status="+event.target.id +"&joinId="+ partyJoin.id, config)
    .then(props.setIsCommentChange(!props.isCommentChange))
    .then(setOnModal(!onModal))
  };
  function modalHandler() {
    setOnModal(!onModal);
  }
  useEffect(() => {
    setPartyJoin(props.partyJoin)
    return () => {
    }
  }, [props.partyJoin])
  if (props.type === "승인대기") {
    return (
      <div className='col-span-2 p-4 bg-green-700 rounded-lg'>
        {!onModal && (
        <button onClick={modalHandler} className='text-center PartyCandidateCard'>
          <img className='w-20 h-20 rounded-full' src={Profile} alt="참가 신청 목록 프로필 이미지" />
          <p className='mt-4'>{partyJoin.applier}</p>
        </button>)}
        {onModal && (
        <div className='fixed top-0 left-0 flex items-center justify-center w-full h-screen bg-black bg-opacity-30'>
          <div className='w-1/4 bg-white rounded-lg'>
            <p className='mt-2 text-lg text-center'>신청 상세 정보</p>
            <div className='grid grid-cols-4 mx-4 mt-2 mb-4'>
              <div className='col-span-1 ml-2'>
                <img src={Profile} className="object-cover w-full h-full rounded-full"  alt="프로필 이미지"/>
              </div>
              <div className="col-span-3 ml-4 text-left">
                <p className='mt-2 text-2xl'>{partyJoin.applier}</p>
                <p className='mt-1'>{partyJoin.message}</p>
              </div>
              <div className='col-span-2 mt-3 ml-4'>
                <button id="1" onClick={joinStatusChange} className='px-4 py-2 text-lg text-gray-700 transition-all bg-green-400 rounded-lg hover:bg-green-700 hover:text-white'>참가 승인</button>
              </div>
              <div className='col-span-2 mt-3 mr-4'>
                <button id="2" onClick={joinStatusChange} className='px-4 py-2 text-lg text-gray-700 transition-all bg-orange-400 rounded-lg hover:bg-orange-700 hover:text-white'>참가 거절</button>
              </div>
            </div>
          </div>
        </div>
      )}
      </div>
        
    )
  }
  else{
    return(
    <div className='col-span-2 p-4 text-center bg-yellow-300 rounded-lg PartyCandidateCard'>
        <img className='w-20 h-20 rounded-full' src={Profile} alt="참가 신청 목록 프로필 이미지" />
        <p className='mt-4'>{partyJoin.applier}</p>
      </div>
    )
  }
}

export default PartyCandidateCard