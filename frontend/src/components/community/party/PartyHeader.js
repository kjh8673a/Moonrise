import React, { Component } from 'react'

export default class PartyHeader extends Component {
  render() {
    return (
        <div class="mt-3 h-10 flex justify-between">
            <div class="flex">
                <div class="flex items-center">정렬 기준 : </div>
                <select class="ml-1 focus:ring-white focus:border-white">
                    <option selected>최신순</option>
                    <option value="popular">인기순</option>
                </select>
                <form class="ml-2">   
                    <div class="h-10 relative">
                        <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                            <svg aria-hidden="true" class="w-5 h-5 text-gray-500 dark:text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg>
                        </div>
                        <input type="search" id="party-search" class="block h-10 w-full pl-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="뒷풀이 검색"/>
                    </div>
                </form>
            </div>
            <div class="flex">
                <button class="bg-transparent hover:bg-gray-500 text-gray-500 hover:text-white border border-white hover:border-transparent rounded">뒷풀이 생성</button>
            </div>
        </div>
    )
  }
}
