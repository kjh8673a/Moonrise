import React from 'react'
import { useNavigate } from 'react-router-dom';

function CommunityNav() {
  const movePage = useNavigate();
  
  function changeBoard(){
    movePage('/community/');
  }
  function changeTalk(){
    movePage('/community/talk');
  }
  function changeParty(){
    movePage('/community/party');
  }

  return (
    <div className='mb-4'>
        <ul class="navbar-nav flex border-b pb-2">
            <li class="flex-1 text-center">
                <bitton class="nav-link text-white hover:text-white" onClick={changeBoard}>게시글</bitton>
            </li>
            <li class="flex-1 text-center">
                <button class="text-gray-400 hover:text-white" onClick={changeTalk}>담소</button>
            </li>
            <li class="flex-1 text-center">
                <button class="text-gray-400 hover:text-white" onClick={changeParty}>뒷풀이</button>
            </li>
        </ul>
    </div>
  )
}

export default CommunityNav