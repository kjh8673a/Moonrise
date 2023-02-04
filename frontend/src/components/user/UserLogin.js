import React from 'react'
import { useNavigate } from 'react-router-dom';
import Kakao from '../../assets/img/kakao.png'

function UserLogin() {
    const movePage = useNavigate();
  
    function gorRegister(){
      movePage('/user/register/');
    }

  return (
    <div className='relative userLogin grid grid-cols-3 h-full content-center bg-black bg-opacity-60'>
        <div  className="absolute col-span-3 text-center duration-300">
            <p className='text-4xl m-8 text-center text-white'>영화보고 달뜬 마음, <span className='text-5xl text-orange-400'>달뜸</span>으로 가져오세요</p>
            <button onClick={gorRegister} className='m-3 text-center rounded-lg py-3 px-6 bg-kakao-bg text-kakao-text hover:opacity-80 transition-all duration-300'>
                <img src={Kakao} alt="카카오 로고" className='inline-block mr-2 h-8'></img>
                <span className='text'><strong>카카오로 달뜸 시작하기</strong></span>
            </button>
        </div>        

    </div>
  )
}

export default UserLogin