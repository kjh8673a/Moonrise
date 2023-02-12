import React from 'react'
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

function CommunityHeader(props) {
  const movePage = useNavigate();
  const isLogin = useSelector(state=> state.member.isLogin);

  function moveWrite(){
    if(isLogin){
        if (props.type === "게시글") {
            movePage('/community/write/board');
        }
        else if (props.type === "담소") {
            movePage('/community/write/talk');
        }
        else if (props.type === "뒷풀이"){
            movePage('/community/write/party');
        }
    }
    else{
        alert("로그인이 필요합니다.")
    }
  }
  return (
    <div className="flex justify-between h-10 mt-3 mb-2">
            <div className="flex">
                <div className="flex items-center text-white">정렬 기준 : </div>
                <select className="ml-1 text-white bg-transparent focus:bg-transparent focus:border-white">
                    <option selected>최신순</option>
                    <option value="popular">인기순</option>
                </select>
                
            </div>
            <div className="flex">
                <button className="px-4 py-2 text-gray-500 bg-transparent border border-white rounded hover:bg-gray-500 hover:text-white hover:border-transparent" onClick={moveWrite}>새로운 {props.type}</button>
            </div>
        </div>
  )
}

export default CommunityHeader
