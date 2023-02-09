import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux';

function PartyEnroll(props) {
  const [requestBody, setRequestBody] = useState({
    partyId:"",
    message:""
  });
  const [joinStatus, setJoinStatus] = useState("")
  const access_token =  useSelector(state => state.member.accessToken);
  const baseURL = process.env.REACT_APP_BASE_URL;
  const config = { 
    headers: {
      "access_token": access_token,
    }
  };
  function partyJoin() {
    axios.post(baseURL+'/api/party/join',requestBody, config)
    .then(response => {console.log(response)})
    props.setIsCommentChange(props.isCommentChange);
  }
  const messageHandler = (event) => {
    setRequestBody((prevState) => {
        return { ...prevState, message: event.target.value }
    });
  };
  useEffect(() => {
    setRequestBody((prevState) => {
      return {...prevState, partyId : props.partyId}
    })
    setJoinStatus(props.joinStatus)
  
    return () => {
    }
  }, [props.joinStatus, props.partyId])
  
  if (joinStatus === "신청안함") {
    return (
      <div className='mt-3 PartyEnroll'>
          <p className='text-lg text-white'>참가 신청</p>
          <div className="px-4 py-3 mt-3 bg-gray-100 rounded-lg">
              <div>
                  <label for="enrollReason" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">참가 사유</label>
                  <textarea type="text" onChange={messageHandler} id="enrollReason" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:outline-none focus:ring-orange-500 focus:border-orange-500 block w-full p-2.5 h-28 resize-none" placeholder="참가 사유에 따라 참가 여부가 결정됩니다." required/>
              </div>
              <div className='text-center'>
                  <button onClick={partyJoin} type="submit" class="text-white bg-orange-600 hover:bg-orange-800 focus:ring-2 focus:outline-none focus:ring-orange-200 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2 mt-2 text-center">참가 신청</button>
              </div>
          </div>
      </div>
    )
  }
  else{
    return(
      <div className="p-4 text-lg text-white">{joinStatus}</div>
    )
  }
}

export default PartyEnroll