import React from 'react'

function PartyDetailCard(props) {
    
    return (
    <div className='mt-5 partyDetailCard'>
        <img src={props.partyDetail.imagePath} className="object-cover w-full h-60" alt="뒷풀이 이미지"/>
        <div className="flex justify-between h-10 bg-white">
            <div className='flex mx-auto'>
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="h-8 mt-1 mr-3">
                <path stroke-linecap="round" stroke-linejoin="round" d="M15 10.5a3 3 0 11-6 0 3 3 0 016 0z" />
                <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 10.5c0 7.142-7.5 11.25-7.5 11.25S4.5 17.642 4.5 10.5a7.5 7.5 0 1115 0z" />
                </svg>
                <p className="mt-2 font-bold">{props.partyDetail.location}</p>
            </div>
            <div className='flex mx-auto'>
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="h-8 mt-1 mr-3">
                <path stroke-linecap="round" stroke-linejoin="round" d="M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 012.25-2.25h13.5A2.25 2.25 0 0121 7.5v11.25m-18 0A2.25 2.25 0 005.25 21h13.5A2.25 2.25 0 0021 18.75m-18 0v-7.5A2.25 2.25 0 015.25 9h13.5A2.25 2.25 0 0121 11.25v7.5m-9-6h.008v.008H12v-.008zM12 15h.008v.008H12V15zm0 2.25h.008v.008H12v-.008zM9.75 15h.008v.008H9.75V15zm0 2.25h.008v.008H9.75v-.008zM7.5 15h.008v.008H7.5V15zm0 2.25h.008v.008H7.5v-.008zm6.75-4.5h.008v.008h-.008v-.008zm0 2.25h.008v.008h-.008V15zm0 2.25h.008v.008h-.008v-.008zm2.25-4.5h.008v.008H16.5v-.008zm0 2.25h.008v.008H16.5V15z" />
                </svg>
                <p className="mt-2 font-bold">{props.date}</p>
            </div>
            <div className='flex mx-auto'>
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="h-8 mt-1 mr-3">
                <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 6a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0zM4.501 20.118a7.5 7.5 0 0114.998 0A17.933 17.933 0 0112 21.75c-2.676 0-5.216-.584-7.499-1.632z" />
                </svg>
                <p className="mt-2 font-bold">{props.partyDetail.partyPeople}명</p>
            </div>
        </div>
        <div className="grid h-32 grid-cols-4">
            <div className='col-span-1 p-3 text-center bg-dal-green'>
                <p className='text-sm text-white'>주최자</p>
                <img src={props.partyDetail.profileImage} className="object-cover w-16 h-16 mx-auto rounded-full"  alt="프로필 이미지"/>
                <p className='mt-2 text-sm text-white'>{props.partyDetail.writer}</p>
            </div>
            <div className='col-span-3 p-3 bg-dal-orange'>
                <span className='font-bold'>주의사항</span>
                <p className="">{props.partyDetail.content}</p>
            </div>
        </div>
    </div>
  )
}

export default PartyDetailCard