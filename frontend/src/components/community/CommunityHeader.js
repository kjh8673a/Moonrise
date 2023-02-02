import React from 'react'
import { useNavigate } from 'react-router-dom';

function CommunityHeader(props) {
  const movePage = useNavigate();
  function moveWrite(){
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
  const searchPlaceholder = props.type+" 검색";
  return (
    <div class="mt-3 h-10 mb-2 flex justify-between">
            <div class="flex">
                <div class="flex items-center text-white">정렬 기준 : </div>
                <select class="ml-1 bg-transparent text-white focus:text-white focus:bg-transparent focus:border-white">
                    <option selected>최신순</option>
                    <option value="popular">인기순</option>
                </select>
                <form class="ml-2">   
                    <div class="h-10 relative">
                        <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                            <svg aria-hidden="true" class="w-5 h-5 text-gray-500 dark:text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg>
                        </div>
                        <input type="text" id="party-search" class="block h-10 w-full pl-10 text-sm text-white border border-gray-400 rounded-lg bg-transparent focus:ring-white focus:border-white dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white" placeholder={searchPlaceholder}/>
                    </div>
                </form>
            </div>
            <div class="flex">
                <button class="bg-transparent px-4 py-2 hover:bg-gray-500 text-gray-500 hover:text-white border border-white hover:border-transparent rounded" onClick={moveWrite}>새로운 {props.type}</button>
            </div>
        </div>
  )
}

export default CommunityHeader
