import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux';

function PartyEnroll(props) {
  const [requestBody, setRequestBody] = useState({
    partyId:"",
    message:""
  });
  const [joinStatus, setJoinStatus] = useState(props.joinStatus)
  const access_token =  useSelector(state => state.member.accessToken);
  const baseURL = process.env.REACT_APP_BASE_URL;
  const config = { 
    headers: {
      "access_token": access_token,
    }
  };

  async function partyJoin() {
    const response = await axios.post(baseURL + '/api/party/join', requestBody, config);
    console.log(response);
    if (response.status === 200) {
      alert("신청이 완료되었습니다!");
    }
    props.setIsCommentChange(!props.isCommentChange);
  }
  const messageHandler = (event) => {
    setRequestBody((prevState) => {
        return { ...prevState, message: event.target.value }
    });
  };
  useEffect(() => {
    setJoinStatus(props.joinStatus)
    setRequestBody((prevState) => {
      return {...prevState, partyId: props.partyId}
    })
    return () => {
    }
  }, [props.joinStatus, props.partyId])
  
  return (
    <div className='mt-3 PartyEnroll'>
    {joinStatus === "신청안함" && (
      <>
        <p className='mt-6 mb-2 text-lg text-white'>참가 신청</p>
        <div className="h-56 p-4 bg-gray-100 rounded-lg">
            <div>
                <label for="enrollReason" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">참가 사유</label>
                <textarea type="text" onChange={messageHandler} id="enrollReason" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:outline-none focus:ring-orange-500 focus:border-dal-orange block w-full p-2.5 h-28 resize-none" placeholder="참가 사유에 따라 참가 여부가 결정됩니다." required/>
            </div>
            <div className='text-center'>
                <button onClick={partyJoin} type="submit" class="bg-dal-orange hover:bg-opacity-70 focus:ring-2 focus:outline-none focus:ring-orange-200 font-medium rounded-lg text-sm w-full px-5 py-2 mt-2 text-center">참가 신청</button>
            </div>
        </div>
      </>
    )}
    {joinStatus === "승인대기" && (
      <div className="h-20 text-lg text-center text-white bg-white rounded-lg bg-opacity-20">
        <p className='pt-5'>
        참가 승인 대기 중입니다...
        </p>
      </div>
      )}
  </div>
  )
}
export default PartyEnroll