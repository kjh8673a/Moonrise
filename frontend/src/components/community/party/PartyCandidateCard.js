import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom';

function PartyCandidateCard(props) {
  const [partyJoin, setPartyJoin] = useState(props.partyJoin);
  const [onModal, setOnModal] =useState(false);
  const access_token = useSelector(state => state.member.accessToken);
  const baseURL = process.env.REACT_APP_BASE_URL;
  const config = { 
    headers: {
      "access_token": access_token,
    }
  };
  const movePage = useNavigate();
  function redirect(){
    movePage('/community/detail/party/'+props.partyId);
  }
  async function joinStatusChange(event) {
    const response = await axios.get(baseURL + "/api/party/join/status?status="+event.target.id +"&joinId="+ partyJoin.id, config);
    console.log(response);
    redirect()
    props.setEmit();
    setOnModal(false);
  }
  function modalHandler() {
    setOnModal(true);
  }
  function modalCloseHandler(){
    setOnModal(false);
  }
  useEffect(() => {
    setPartyJoin(props.partyJoin)
    return () => {}
  }, [props.partyJoin])
  
  if (props.type === "승인대기") {
    return (
      <div className='col-span-2'>
        <div onClick={modalHandler} className='py-3 rounded-lg bg-dal-green hover:bg-opacity-70'>
          <img className='w-16 h-16 mx-auto rounded-full' src={partyJoin.imagePath} alt="참가 신청 목록 프로필 이미지" />
          <p className='mt-4 text-center text-white'>{partyJoin.applier}</p>
        </div>
        {onModal && (
        <div className='fixed top-0 left-0 flex items-center justify-center w-full h-screen bg-black bg-opacity-30'>
          <div className='relative w-1/4 bg-white rounded-lg'>
            <p className='flex-1 mt-2 text-lg text-center'>신청 상세 정보</p>
            <button className='absolute top-0 right-0 float-right mt-2 mr-2' onClick={modalCloseHandler}>
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>
            <div className='grid grid-cols-4 gap-2 mx-4 mt-2 mb-4'>
              <div className='col-span-1 ml-2'>
                <img src={partyJoin.imagePath} className="object-cover w-full h-full rounded-full"  alt="프로필 이미지"/>
              </div>
              <div className="col-span-3 ml-4 text-left">
                <p className='mt-2 text-2xl'>{partyJoin.applier}</p>
                <p className='mt-1'>{partyJoin.message}</p>
              </div>
              <div className='col-span-2 mt-3'>
                <button id="1" onClick={joinStatusChange} className='w-full py-2 text-lg text-white transition-all rounded-lg bg-dal-green hover:bg-opacity-80 hover:text-white'>참가 승인</button>
              </div>
              <div className='col-span-2 mt-3'>
                <button id="2" onClick={joinStatusChange} className='w-full py-2 text-lg text-gray-700 transition-all rounded-lg bg-dal-orange hover:bg-opacity-80 hover:text-white'>참가 거절</button>
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
    <div className='col-span-2 p-2 text-center bg-yellow-300 rounded-lg PartyCandidateCard'>
        <img className='w-16 h-16 mx-auto mt-2 rounded-full shadow shadow-gray-700' src={partyJoin.imagePath} alt="참가 신청 목록 프로필 이미지" />
        <p className='mt-4 text-xs font-bold'>{partyJoin.applier}</p>
      </div>
    )
  }
}

export default PartyCandidateCard