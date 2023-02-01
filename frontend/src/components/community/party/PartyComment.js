import React from 'react'

function PartyComment() {
  return (
    <div className="partyComment">
          <p className='text-lg text-white'>문의</p>
          <div className="mt-3 rounded-lg bg-gray-100">
            <div className="commentList">
                <div className='comment border-b-2 mx-2 p-2'>
                <div className='flex justify-between'>
                    <div className="flex gap-1">
                    <p className='text-sm'>최현인</p>
                    <p className='text-xs pt-1'>2023.01.31 17:00</p>
                    </div>
                    <div className="flex gap-1">
                    {/* <p className='text-xs pt-1'>수정하기</p> */}
                    <p className='text-xs pt-1'>답글달기</p>
                    <p className='text-xs pt-1'>신고하기</p>
                    </div>
                </div>
                <p className='mt-1'>준비물이 따로 있을까요?</p>          
                </div>
                <div className='flex border-b-2 ml-2'>
                <div className=''>
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="text-gray-500 mt-5 ml-3 w-5 h-5">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M4.5 4.5l15 15m0 0V8.25m0 11.25H8.25" />
                    </svg>
                </div>
                <div className='mx-2 p-2 w-full'>
                    <div className='flex justify-between'>
                    <div className="flex gap-1">
                        <p className='text-sm'>원진아</p>
                        <p className='text-xs pt-1'>2023.01.31 17:30</p>
                    </div>
                    <div className="flex gap-1">
                        {/* <p className='text-xs pt-1'>수정하기</p> */}
                        <p className='text-xs pt-1'>답글달기</p>
                        <p className='text-xs pt-1'>신고하기</p>
                    </div>
                    </div>
                    <p className='mt-1'>준비물은 따로 필요 없습니다!</p>          
                </div>
                </div>
            </div>
            <div className="commentWrite grid grid-cols-10 gap-2 items-center px-2 py-2">
              <div className='col-span-1'>
                <p className='text-xs text-center pt-1'>비공개</p>
                <input id="checked-checkbox" type="checkbox" value="" class="mx-3 mt-1 w-4 h-4 text-orange-600 bg-gray-100 border-gray-300 rounded focus:ring-orange-600"/>
              </div>
              <div className="col-span-8">
                <textarea id="chat" rows="1" className="block p-2.5 w-full text-sm text-gray-900 bg-white rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 resize-none" placeholder="문의사항을 남겨주세요"></textarea>
              </div>
              <div className="col-span-1">
                <button type="submit" className="inline-flex justify-center ml-2 text-orange-600 rounded-full cursor-pointer hover:bg-orange-100 dark:text-orange-500 dark:hover:bg-gray-600">
                  <svg aria-hidden="true" className="w-6 h-6 rotate-90" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M10.894 2.553a1 1 0 00-1.788 0l-7 14a1 1 0 001.169 1.409l5-1.429A1 1 0 009 15.571V11a1 1 0 112 0v4.571a1 1 0 00.725.962l5 1.428a1 1 0 001.17-1.408l-7-14z"></path></svg>
                  <span className="sr-only">Send message</span>
                </button>
              </div>
            </div>
          </div>
        </div>

  )
}

export default PartyComment
