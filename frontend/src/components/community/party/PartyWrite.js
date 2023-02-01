import React from 'react'
import { useNavigate } from 'react-router-dom';

function PartyWrite() {
  const movePage = useNavigate();
  
  function changeBoard(){
    movePage('/community/list/party');
  }
  return (
    <div className='partyWrite mx-64 mt-10'>
        <div className="grid grid-cols-3">
            <div className="col-span-1">
                <button className='text-white mt-2' onClick={changeBoard}>&lt; 이전으로</button>
            </div>
            <div className='col-span-1'>
                <p className='text-2xl text-center text-white'>새로운 뒷풀이</p>
            </div>
        </div>
        <div className='bg-gray-600 bg-opacity-30 rounded-lg mt-4 py-2'>
            <div className="grid grid-cols-2">
                <div className="col-span-1">
                    <div className='relative m-4'>
                        <p className='text-gray-300'>제목</p>
                        <input type="text" id="title" class="block py-2.5 px-0 w-full text-sm text-gray-300 bg-transparent border-0 border-b-2 border-gray-300 appearance-none  focus:outline-none focus:ring-0 focus:border-orange-300 peer" placeholder="" />
                        <label for="title" class="absolute text-sm text-gray-300 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-orange-300 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">뒷풀이 제목을 입력해주세요</label>
                    </div>
                    <div className='relative m-4'>
                        <p className='text-gray-300'>날짜</p>
                        <input type="text" id="title" class="block py-2.5 px-0 w-full text-sm text-gray-300 bg-transparent border-0 border-b-2 border-gray-300 appearance-none  focus:outline-none focus:ring-0 focus:border-orange-300 peer" placeholder="" />
                        <label for="title" class="absolute text-sm text-gray-300 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-orange-300 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">뒷풀이 제목을 입력해주세요</label>
                    </div>
                    <div className='relative m-4'>
                        <p className='text-gray-300'>대표 이미지</p>
                        <div class="flex items-center justify-center w-full mt-4">
                            <label for="dropzone-file" class="flex flex-col items-center justify-center w-full h-64 border-2 border-white border-dashed rounded-lg cursor-pointer hover:bg-gray-600 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
                                <div class="flex flex-col items-center justify-center pt-5 pb-6">
                                    <svg aria-hidden="true" class="w-10 h-10 mb-3 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"></path></svg>
                                    <p class="mb-2 text-sm text-gray-500 dark:text-gray-400">Click to upload</p>
                                    <p class="text-xs text-white"> 파일 형식은 PNG, JPG 만 가능합니다</p>
                                </div>
                                <input id="dropzone-file" type="file" class="hidden" />
                            </label>
                        </div>      
                    </div>
                    
                </div>
                <div className="col-span-1">
                    <div className='relative m-4'>
                        <p className='text-gray-300'>장소</p>
                        <input type="text" id="title" class="block py-2.5 px-0 w-full text-sm text-gray-300 bg-transparent border-0 border-b-2 border-gray-300 appearance-none  focus:outline-none focus:ring-0 focus:border-orange-300 peer" placeholder="" />
                        <label for="title" class="absolute text-sm text-gray-300 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-orange-300 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">뒷풀이 제목을 입력해주세요</label>
                    </div>
                    <div className='relative m-4'>
                        <p className='text-gray-300'>인원 수</p>
                        <input type="text" id="title" class="block py-2.5 px-0 w-full text-sm text-gray-300 bg-transparent border-0 border-b-2 border-gray-300 appearance-none  focus:outline-none focus:ring-0 focus:border-orange-300 peer" placeholder="" />
                        <label for="title" class="absolute text-sm text-gray-300 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-orange-300 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">뒷풀이 제목을 입력해주세요</label>
                    </div>
                    <div className='relative m-4'>
                        <p className='text-gray-300'>주의 사항</p>
                        <textarea class="block mt-4 py-2.5 px-2 h-44 w-full resize-none text-sm text-gray-300 bg-transparent border-2 rounded-lg border-gray-300 appearance-none  focus:outline-none focus:ring-0 focus:border-orange-300 peer" placeholder="" />
                    </div>
                </div>
            </div>
            <div className='text-center'>
                <button className='rounded-lg px-4 py-2 bg-orange-300 hover:bg-orange-600 hover:text-white'>뒷풀이 모집 시작</button>
            </div>
        </div>
    </div>
  )
}

export default PartyWrite
