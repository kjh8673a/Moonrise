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
  const searchPlaceholder = props.type+" 검색";
  return (
    <div className="flex justify-between h-10 mt-3 mb-2">
            <div className="flex">
                <div className="flex items-center text-white">정렬 기준 : </div>
                <select className="ml-1 text-white bg-transparent focus:text-white focus:bg-transparent focus:border-white">
                    <option selected>최신순</option>
                    <option value="popular">인기순</option>
                </select>
                <form className="ml-2">   
                    <div className="relative h-10">
                        <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                            <svg aria-hidden="true" className="w-5 h-5 text-gray-500 dark:text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg>
                        </div>
                        <input type="text" id="party-search" className="block w-full h-10 pl-10 text-sm text-white bg-transparent border border-gray-400 rounded-lg focus:ring-white focus:border-white dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white" placeholder={searchPlaceholder}/>
                    </div>
                </form>
            </div>
            <div className="flex">
                <button className="px-4 py-2 text-gray-500 bg-transparent border border-white rounded hover:bg-gray-500 hover:text-white hover:border-transparent" onClick={moveWrite}>새로운 {props.type}</button>
            </div>
        </div>
  )
}

export default CommunityHeader
