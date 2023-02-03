import React from 'react'
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import CardImg from '../../../assets/img/CardImg.jpg'
import { setPartyId } from '../../../feature/reducer/PartyReducer';

function PartyCard(props) {
  const movePage = useNavigate();
  const dispatch = useDispatch();
  
  function goDetail(){
    dispatch(setPartyId(props.partyId));
    movePage('/community/detail/party');
  }
  return (
    <div class="PartyCard rounded overflow-hidden shadow-lg mx-1" onClick={goDetail}>
      <img src={CardImg} class="h-36 w-full object-cover" alt="뒷풀이 카드 이미지"/>
        <div class="px-2 py-2 bg-orange-500">
          <div class="font-bold text-l mb-2">{props.title}</div>
          <div class="flex">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
              <path stroke-linecap="round" stroke-linejoin="round" d="M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 012.25-2.25h13.5A2.25 2.25 0 0121 7.5v11.25m-18 0A2.25 2.25 0 005.25 21h13.5A2.25 2.25 0 0021 18.75m-18 0v-7.5A2.25 2.25 0 015.25 9h13.5A2.25 2.25 0 0121 11.25v7.5m-9-6h.008v.008H12v-.008zM12 15h.008v.008H12V15zm0 2.25h.008v.008H12v-.008zM9.75 15h.008v.008H9.75V15zm0 2.25h.008v.008H9.75v-.008zM7.5 15h.008v.008H7.5V15zm0 2.25h.008v.008H7.5v-.008zm6.75-4.5h.008v.008h-.008v-.008zm0 2.25h.008v.008h-.008V15zm0 2.25h.008v.008h-.008v-.008zm2.25-4.5h.008v.008H16.5v-.008zm0 2.25h.008v.008H16.5V15z" />
            </svg>
            <p class="text-center ml-2 w-2/6 text-sm">{props.partyDate}</p>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="ml-4 w-5 h-5">
              <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 6a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0zM4.501 20.118a7.5 7.5 0 0114.998 0A17.933 17.933 0 0112 21.75c-2.676 0-5.216-.584-7.499-1.632z" />
            </svg>
            <p class="text-center w-2/6 ml-2 text-sm">{props.partyPeople}명</p>
          </div>
          <div class="flex mt-2">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
            <path stroke-linecap="round" stroke-linejoin="round" d="M15 10.5a3 3 0 11-6 0 3 3 0 016 0z" />
            <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 10.5c0 7.142-7.5 11.25-7.5 11.25S4.5 17.642 4.5 10.5a7.5 7.5 0 1115 0z" />
          </svg>
            <p class="ml-2 text-center w-44 text-sm">서울시 종로구</p>
          </div>
        </div>
        {/* <div class="flex px-2 py-2">
          <p class="flex-1 text-center text-sm ">#소통</p> 
          <p class="flex-1 text-center text-sm ">#사진</p>
          <p class="flex-1 text-center text-sm">#덕질</p>
        </div> */}
    </div>
  )
}

export default PartyCard