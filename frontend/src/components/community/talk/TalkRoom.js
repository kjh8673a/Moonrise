import React, { useEffect, useRef, useState } from 'react'
import Profile from '../../../assets/img/profile.png'
import SockJS from 'sockjs-client';
import  { CompatClient, Stomp } from '@stomp/stompjs'
import axios from 'axios';
import { useSelector } from 'react-redux';

function TalkRoom(props) {
    
    const client = useRef(<CompatClient/>);
    const [findCnt, setFindCnt] = useState(0)
    const roomId = props.talkDetail.debateId;
    const user = useSelector((state) => (state.member.nickname));
    const [chat, setChat] = useState([]);
    const baseURL = process.env.REACT_APP_BASE_URL;

    useEffect(() => {
        console.log("hi");
        axios.get(baseURL + "/chat/debate/pastChats?debateId="+ roomId + "&findCnt="+findCnt)
        .then(response => {
            console.log(response)
            console.log(response.data.data)
            response.data.data.recentChats.map((chatOne) => (
                setChat((prev) => [chatOne, ...prev])
            ))
        })
        return() => {}
    }, [baseURL, findCnt, roomId])

    useEffect(() => {
        console.log("hi");
        client.current = Stomp.over(() => {
            const sock = new SockJS(baseURL + "/chat/socket")
            return sock;
        });
        client.current.connect({},
        () => {              
            client.current.subscribe(
                "/sub/chat/room/"+roomId,
            (message) => {
                setChat((prev) => ([...prev, JSON.parse(message.body)]));
            },{}
        )});
        return () => {
            // 여기에 채팅방 나가는 요청
            client.current.disconnect();
        }  
    }, [baseURL, roomId])
    
    function countInc(){
        setFindCnt(findCnt+1);
    }
    function send(){
        const msg = {
            debateId: roomId,
            content: "하이욤",
            writer: user,
          };
        
        client.current.send("/pub/chat/message", {} , JSON.stringify(msg))
    }
  return (
    <div className='p-2 my-4 bg-gray-200 rounded-lg h-96'>
        <div className='grid grid-cols-3'>

        { chat.map((chatOne) => {
            if (chatOne.writer === user) {
                return(
                    <div className='col-span-2 col-start-2 m-2 justify-items-end'>
                        <div className='grid grid-cols-6'>
                                <div className='col-span-1 col-start-6'>
                                    <img className='w-10 rounded-full' src={Profile} alt="" />
                                </div>
                        </div>
                        <div className='p-2 mt-2 bg-white rounded-lg'>
                            {chatOne.content}
                        </div>
                    </div>
                )
                    
            }
            else{
                return(
                    <div className='col-span-2 m-2'>
                        <div className='flex'>
                            <img className='w-10 rounded-full' src={Profile} alt="" />
                            <div className='ml-2'>
                                <p className=''>{chatOne.writer}</p>
                            </div>
                        </div>
                        <div className='p-2 mt-2 bg-white rounded-lg'>
                            {chatOne.content}
                        </div>
                    </div>
                )
                
            }
        })}
        </div>
        <button onClick={send}>제출</button>
        <button onClick={countInc}>제출</button>
    </div>
  )
}

export default TalkRoom