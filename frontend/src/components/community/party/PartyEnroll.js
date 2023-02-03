import React from 'react'

function PartyEnroll() {
  return (
    <div className='PartyEnroll mt-3'>
        <p className='text-white text-lg'>참가 신청</p>
        <div className="mt-3 px-4 py-3 rounded-lg bg-gray-100">
            <div>
                <label for="enrollReason" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">참가 사유</label>
                <textarea type="text" id="enrollReason" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:outline-none focus:ring-orange-500 focus:border-orange-500 block w-full p-2.5 h-28 resize-none" placeholder="참가 사유에 따라 참가 여부가 결정됩니다." required/>
            </div>
            <div className='text-center'>
                <button type="submit" class="text-white bg-orange-600 hover:bg-orange-800 focus:ring-2 focus:outline-none focus:ring-orange-200 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2 mt-2 text-center">참가 신청</button>
            </div>
        </div>
    </div>
  )
}

export default PartyEnroll