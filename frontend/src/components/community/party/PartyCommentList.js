import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux';

function PartyCommentList(props) {
    const [requestBody, setRequestBody] = useState({
        "content": "",
        "groupId": "",
        "isNestedComment": 1,
        "partyId": "",
        "showPublic": true
    });
    const [partyComment, setPartyComment] = useState({})
    const [visible, setVisible] = useState(false);
    const [visibleClass , setVisibleClass] = useState("hidden items-center grid-cols-10 px-2 py-2 border-b-2 commentWrite")
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
      };
      const showPublicHandler = (event) => {
        setRequestBody((prevState) => {
            return { ...prevState, showPublic: !event.target.checked }
        });
      }
      const visibleHandler = () => {
        setVisible(!visible);
        if (visible) {
            setVisibleClass("hidden")
        }
        else{
            setVisibleClass("grid items-center grid-cols-10 px-2 py-2 border-b-2 commentWrite")
        }
    };
      function commentWrite(){
        axios.post(baseURL+'/api/party/comment/write', requestBody, config)
          .then(response => {
            console.log(response);
          })
          .then(
            setRequestBody((prevState) => {
              return { ...prevState, showPublic: true, content: "" }
            })
          );
        props.setIsCommentChange(!props.isCommentChange)
      }
      useEffect(() => {
        setPartyComment(props.partyComment)
        setRequestBody((prevState) => {
            return {...prevState, partyId: props.partyId, groupId: props.partyComment.groupId}
        })
        return () => {
        }
      }, [props.partyComment, props.partyId])
    if (partyComment.isNestedComment === 0) {
        if (partyComment.showPublic){
          return(
            <div className='comment'>
            <div className='p-2 mx-2 border-b-2 comment'>
              <div className='flex justify-between'>
                  <div className="flex gap-1">
                  <p className='text-sm'>{partyComment.writer}</p>
                  <p className='pt-1 text-xs'>{partyComment.commentWriteTime}</p>
                  </div>
                  <div className="flex gap-1">
                  {partyComment.showPublic && (<p className='pt-1 text-xs'>수정하기</p>)}
                  <button onClick={visibleHandler} className='pt-1 text-xs'>답글달기</button>
                  </div>
              </div>
              <p className='mt-1'>{partyComment.content}</p>          
            </div>
            <div id={partyComment.id} className={visibleClass}>
                    <div className='col-span-1'>
                        <p className='pt-1 text-xs text-center'>비공개</p>
                        <input id="checked-checkbox" checked={!requestBody.showPublic} onChange={showPublicHandler} type="checkbox" value="" className="w-4 h-4 mx-3 mt-1 bg-gray-100 border-gray-300 rounded accent-orange-600 focus:ring-orange-600"/>
                    </div>
                    <div className="col-span-8">
                        <textarea id="chat" rows="1" onChange={contentHandler} className="block p-2.5 w-full text-sm text-gray-900 bg-white rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 resize-none" value={requestBody.content} placeholder="문의사항을 남겨주세요"></textarea>
                    </div>
                    <div className="col-span-1">
                        <button type="submit" onClick={commentWrite} className="inline-flex justify-center ml-2 text-orange-600 rounded-full cursor-pointer hover:bg-orange-100 dark:text-orange-500 dark:hover:bg-gray-600" >
                        <svg aria-hidden="true" className="w-6 h-6 rotate-90" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M10.894 2.553a1 1 0 00-1.788 0l-7 14a1 1 0 001.169 1.409l5-1.429A1 1 0 009 15.571V11a1 1 0 112 0v4.571a1 1 0 00.725.962l5 1.428a1 1 0 001.17-1.408l-7-14z"></path></svg>
                        </button>
                    </div>
                </div>
          </div>
          )
        }
        else{
          return(
            <div className='comment'>
              <div className='p-2 mx-2 bg-gray-300 bg-opacity-50 border-b-2 comment'>
                <p className='mt-1'>비공개 문의 입니다.</p>          
              </div>
            </div>
          )
        }
      }
      else {
        if(partyComment.showPublic) {
          return(
            <div>
                <div className="flex py-3 mx-2 border-b-2 nestedComment">
                    <div className='pl-2 nestedImg'>
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="text-gray-500 w-7 h-7">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M4.5 4.5l15 15m0 0V8.25m0 11.25H8.25" />
                        </svg>
                    </div>
                    <div className='w-full ml-3'>
                        <div className='flex justify-between'>
                        <div className="flex gap-1">
                            <p className='text-sm'>{partyComment.writer}</p>
                            <p className='pt-1 text-xs'>{partyComment.commentWriteTime}</p>
                        </div>
                        <div className="flex gap-1">
                            {partyComment.showPublic && (<p className='pt-1 text-xs'>수정하기</p>)}
                            <button onClick={visibleHandler} className='pt-1 text-xs'>답글달기</button>
                        </div>
                        </div>
                        <p className='mt-1'>{partyComment.content}</p>          
                    </div>
                </div>
                <div id={partyComment.id} className={visibleClass}>
                    <div className='col-span-1'>
                    </div>
                    <div className='col-span-1'>
                        <p className='pt-1 text-xs text-center'>비공개</p>
                        <input id="checked-checkbox" checked={!requestBody.showPublic} onChange={showPublicHandler} type="checkbox" value="" className="w-4 h-4 mx-3 mt-1 bg-gray-100 border-gray-300 rounded accent-orange-600 focus:ring-orange-600"/>
                    </div>
                    <div className="col-span-7">
                        <textarea id="chat" rows="1" onChange={contentHandler} className="block p-2.5 w-full text-sm text-gray-900 bg-white rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 resize-none" value={requestBody.content} placeholder="문의사항을 남겨주세요"></textarea>
                    </div>
                    <div className="col-span-1">
                        <button type="submit" onClick={commentWrite} className="inline-flex justify-center ml-2 text-orange-600 rounded-full cursor-pointer hover:bg-orange-100 dark:text-orange-500 dark:hover:bg-gray-600" >
                        <svg aria-hidden="true" className="w-6 h-6 rotate-90" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M10.894 2.553a1 1 0 00-1.788 0l-7 14a1 1 0 001.169 1.409l5-1.429A1 1 0 009 15.571V11a1 1 0 112 0v4.571a1 1 0 00.725.962l5 1.428a1 1 0 001.17-1.408l-7-14z"></path></svg>
                        </button>
                    </div>
                </div>
            </div>
          )
        }
        else{
          return(
            <div className='flex py-3 mx-2 bg-gray-300 border-b-2 blindNestedComment'>
              <div className='pl-2 nestedImg'>
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="text-gray-500 w-7 h-7">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M4.5 4.5l15 15m0 0V8.25m0 11.25H8.25" />
                  </svg>
              </div>
              <div className='ml-2'>
              <div className='flex justify-between'>
                    <div className="flex gap-1">
                        <p className='text-sm'>작성일</p>
                        <p className='pt-1 text-xs'>{partyComment.commentWriteTime}</p>
                    </div>
                </div>
                <p className='mt-1 content'>비공개 문의 입니다.</p>          
              </div>
            </div>
          )
          
        }
      }        
}

export default PartyCommentList