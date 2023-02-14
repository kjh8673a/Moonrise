import React, { useEffect, useRef, useState } from 'react'
import Profile from '../../../assets/img/profile.png'
import SockJS from 'sockjs-client';
import  { CompatClient, Stomp } from '@stomp/stompjs'
import axios from 'axios';
import { useSelector } from 'react-redux';

function TalkRoom(props) {
    
    const client = useRef(<CompatClient/>);
    const [findCnt, setFindCnt] = useState(0);
    const roomId = props.talkDetail.debateId;
    const user = useSelector((state) => (state.member.nickname));
    const baseURL = process.env.REACT_APP_BASE_URL;
    const [chat, setChat] = useState([]);
    const [msg, setMsg] = useState("");
    useEffect(() => {
        axios.get(baseURL + "/chat/debate/pastChats?debateId="+ roomId + "&findCnt="+findCnt)
        .then(response => {
            console.log(response)
            if (response.data.status_code === 400) {
                alert("이전 채팅 내역이 없습니다.")
            }
            else{
                if (findCnt === 0) {
                    response.data.data.recentChats.map((chatOne) => (
                        setChat((prev) => [...prev, chatOne])
                    ))
                }
                else {
                    response.data.data.recentChats.map((chatOne) => (
                        setChat((prev) => [chatOne, ...prev])
                    ))  
                }
            }   
            });
        return() => {}
    }, [baseURL, findCnt, roomId])

    useEffect(() => {
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
            axios.get(baseURL + "/chat/debate/exit?debateId=" + roomId);
            client.current.disconnect();
        }  
    }, [baseURL, roomId])
    
    function countInc(){
        setFindCnt(findCnt+1);
    }
    function msgHandler(event){
        setMsg(event.target.value);
    }
    const handleOnKeyPress = (e) => {
        if (e.key === "Enter") {
            if (msg !== "") {
                send(); // Enter 입력이 되면 클릭 이벤트 실행
            }
        }
      };
    function submitClick(){
        send()
    }
    function send(){
        setMsg("");
        const sendMsg = {
            debateId: roomId,
            content: msg,
            writer: user,
          };
        
        client.current.send("/pub/chat/message", {} , JSON.stringify(sendMsg))
    }
  return (
    <div className='p-2 mt-4 mb-12 bg-gray-200 rounded-lg h-96 overflow-y-auto'>
        <div className='grid grid-cols-5'>
            <div className='col-span-5'>
                <button onClick={countInc} className="w-full text-center bg-gray-400 bg-opacity-20 hover:bg-opacity-40">이전 내역</button>
            </div>
        { chat.map((chatOne) => {
            if (chatOne.writer === user) {
                return(
                    <div className='col-span-3 col-start-3 m-2 justify-items-end'>
                        <div className='p-2 mt-2 bg-orange-200 rounded-lg'>
                            <p>
                                {chatOne.content}
                            </p>
                        </div>
                    </div>
                )
                    
            }
            else{
                return(
                    <div className='col-span-4 m-2'>
                        <div className='grid grid-cols-5'>
                            <div className='col-span-1'>
                                <img className='w-10 rounded-full' src={Profile} alt="" />
                            </div>
                            <div className='col-span-4'>
                                <p className=''>{chatOne.writer}</p>
                                <p className='bg-white p-2 rounded-lg'>
                                    {chatOne.content}
                                </p>
                            </div>
                        </div>
                    </div>
                )
                
            }
        })}
        </div>
        <div className="fixed bottom-1 grid items-center grid-cols-10 gap-2 py-2 commentWrite">
          <div className="col-span-9">
            <input type="text" onKeyPress={handleOnKeyPress} id="chat" rows="1" onChange={msgHandler} className="block p-2.5 w-full text-sm text-gray-900 bg-white rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 resize-none" value={msg}></input>
          </div>
          <div className="col-span-1">
            <button type="submit" onClick={submitClick} className="inline-flex justify-center ml-2 text-orange-600 rounded-full cursor-pointer hover:bg-orange-100 dark:text-orange-500 dark:hover:bg-gray-600" >
              <svg aria-hidden="true" className="w-6 h-6 rotate-90" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M10.894 2.553a1 1 0 00-1.788 0l-7 14a1 1 0 001.169 1.409l5-1.429A1 1 0 009 15.571V11a1 1 0 112 0v4.571a1 1 0 00.725.962l5 1.428a1 1 0 001.17-1.408l-7-14z"></path></svg>
            </button>
          </div>
        </div>
        
    </div>
  )
}

export default TalkRoom