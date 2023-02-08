import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';

function CommunityNav() {
  const movePage = useNavigate();
  
  const [menu, setMenu] = useState("board");
  const selected = "text-white transition-opacity opacity-100";
  const notSelected = "text-white transition-opacity opacity-50 hover:opacity-100";

  function changeBoard(){
    movePage('/community/list/');
    setMenu("board");
  }
  function changeTalk(){
    movePage('/community/list/talk');
    setMenu("talk");
  }
  function changeParty(){
    movePage('/community/list/party');
    setMenu("party");
  }
  
  return (
    <div className='mb-4'>
        <ul className="flex pb-2 border-b navbar-nav">
            <li className="flex-1 text-center">
                <bitton className={menu === "board" ? selected : notSelected} onClick={changeBoard}>게시글</bitton>
            </li>
            <li className="flex-1 text-center">
                <button className={menu === "talk" ? selected : notSelected}  onClick={changeTalk}>담소</button>
            </li>
            <li className="flex-1 text-center">
                <button className={menu === "party" ? selected : notSelected} onClick={changeParty}>뒷풀이</button>
            </li>
        </ul>
    </div>
  )
}

export default CommunityNav