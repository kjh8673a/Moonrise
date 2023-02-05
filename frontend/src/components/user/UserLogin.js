import React from 'react'
import Kakao from '../../assets/img/kakao.png'

function UserLogin() {
  
  const kakao_rest_api_key = process.env.REACT_APP_KAKAO_REST_API_KEY;
  const kakao_redirect_uri = process.env.REACT_APP_KAKAO_REDIRECT_URI;

  async function goKaKaoLogin(){
    window.location.href = 'https://kauth.kakao.com/oauth/authorize?client_id='+kakao_rest_api_key+'&redirect_uri='+ kakao_redirect_uri+'&response_type=code';
  }

  return (
    <div className='grid content-center h-full grid-cols-3 bg-black userLogin bg-opacity-60'>
        <div  className="col-span-3 text-center duration-300">
            <p className='m-8 text-4xl text-center text-white'>영화보고 달뜬 마음, <span className='text-5xl text-orange-400'>달뜸</span>으로 가져오세요</p>
            <button onClick={goKaKaoLogin} className='px-6 py-3 m-3 text-center transition-all duration-300 rounded-lg bg-kakao-bg text-kakao-text hover:opacity-80'>
                <img src={Kakao} alt="카카오 로고" className='inline-block h-8 mr-2'></img>
                <span className='text'><strong>카카오로 달뜸 시작하기</strong></span>
            </button>
        </div>        

    </div>
  )
}

export default UserLogin