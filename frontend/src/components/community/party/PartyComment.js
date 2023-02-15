import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux';
import PartyCommentList from './PartyCommentList';


function PartyComment(props) {
  const [requestBody, setRequestBody] = useState({
      "content": "",
      "groupId": 0,
      "isNestedComment": 0,
      "partyId": props.partyId,
      "showPublic": true
  });
  const [partyComments, setPartyComments] = useState([])
  const access_token = useSelector(state => state.member.accessToken);
  const baseURL = process.env.REACT_APP_BASE_URL;
  const config = {
    headers: {
      "access_token" : access_token
    }
  }
  const contentHandler = (event) => {
    setRequestBody((prevState) => {
    	return { ...prevState, content: event.target.value }
    });
  }
  const showPublicHandler = (event) => {
    setRequestBody((prevState) => {
    	return { ...prevState, showPublic: !event.target.checked }
    });
  }
  async function commentWrite(){
    await axios.post(baseURL+'/api/party/comment/write', requestBody, config)  
    setRequestBody((prevState) => {
          return { ...prevState, showPublic: true, content: "" }
    })
    const res = await axios.get(baseURL+ '/api/party/read/'+props.partyId, config)
    setPartyComments(res.data.data.findParty.partyComments);
  }
  async function commetReply(){
    const res = await axios.get(baseURL+ '/api/party/read/'+props.partyId, config)
    setPartyComments(res.data.data.findParty.partyComments);
  }
  useEffect(() => {
    const commentDiv = document.getElementById('commentDiv');
    commentDiv.scrollTo({ top: commentDiv.scrollHeight });
  });
  useEffect(() => {
    setPartyComments(props.partyComments);
  
    return () => {}
  }, [props.partyComments])
  
  return (
    <div className="partyComment">
      <p className='my-2 text-lg text-white'>문의</p>
      <div className="bg-gray-100 rounded-lg">
        <div id="commentDiv" className="h-64 max-h-screen overflow-y-auto commentList">
          {partyComments.map((partyComment) => (
            <PartyCommentList key={partyComment.id} commentReply={commetReply} partyId={props.partyId} partyComment={partyComment}/>
            ))}
        </div>
        <div className="grid items-center grid-cols-10 gap-2 px-2 py-2 commentWrite">
          <div className='col-span-1'>
            <p className='pt-1 text-xs text-center'>비공개</p>
            <input id="checked-checkbox" checked={!requestBody.showPublic} onChange={showPublicHandler} type="checkbox" value="" className="w-4 h-4 mx-3 mt-1 bg-gray-100 border-gray-300 rounded accent-dal-orange focus:ring-dal-orange"/>
          </div>
          <div className="col-span-8">
            <textarea id="chat" rows="1" onChange={contentHandler} className="block p-2.5 w-full text-sm text-gray-900 bg-white rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 resize-none" value={requestBody.content} placeholder="문의사항을 남겨주세요"></textarea>
          </div>
          <div className="col-span-1">
            <button type="submit" onClick={commentWrite} className="inline-flex justify-center ml-2 rounded-full cursor-pointer text-dal-orange hover:bg-orange-100" >
              <svg aria-hidden="true" className="w-6 h-6 rotate-90" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M10.894 2.553a1 1 0 00-1.788 0l-7 14a1 1 0 001.169 1.409l5-1.429A1 1 0 009 15.571V11a1 1 0 112 0v4.571a1 1 0 00.725.962l5 1.428a1 1 0 001.17-1.408l-7-14z"></path></svg>
            </button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default PartyComment
