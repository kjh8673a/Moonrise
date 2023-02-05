import axios from 'axios';
import React, { useState } from 'react'
import { useSelector } from 'react-redux';

const getButtons = () => {
    const genreList = ["코미디", "SF", "멜로", "액션", "범죄", "스릴러", "전쟁", "판타지", "스포츠"];
  
      const buttonList = genreList.map((genre) => (
        <div className="col-span-1 py-4 text-lg text-center text-white border-2 border-white rounded-lg">
            {genre}
        </div>
      ))
      
    return (
        buttonList
    );
  }

function UserRegister() {
    const [requestBody, setRequestBody] = useState({
        access_token: useSelector(state=> state.member.accessToken),
        nickname: useSelector(state=> state.member.nickname),
        gender: "M",
        like_genre: ["SF", "코미디", "로맨스"],
    });
      
      const nicknameHandler = (event) => {
        setRequestBody((prevState) => {
            return { ...prevState, nickname: event.target.value }
        });
      }
      // const genderHandler = (event) => {
      //   setRequestBody((prevState) => {
      //       return { ...prevState, gender: "2023-02-02T06:23:05.082Z" }
      //   });
      // }
      // const genreHandler = (event) => {
      //   setRequestBody((prevState) => {
      //       return { ...prevState, like_genre: event.target.value }
      //   });
      // }
  async function register(){
      const config = { 
          headers: {
            "Content-Type": "application/json",
            }
          }
          console.log(requestBody);
      axios.post('http://3.35.149.202:9002/auth/member/join', requestBody, config)
          .then(response => {
              console.log(response);
          });
  }
  return (
    <div className='grid content-center h-full grid-cols-3 bg-black UserRegister bg-opacity-60'>
        <div className='col-span-3 text-center'>
            <p className='m-4 text-4xl text-white'>추가 정보 입력</p>
            <div className='grid grid-cols-3'>
                <div className="col-span-1 col-start-2 bg-black rounded-lg bg-opacity-60">
                    <div className='m-10 text-left'>
                        <p className='text-gray-300'>닉네임</p>
                        <input type="text" id="title" onChange={nicknameHandler} className="block py-2.5 px-0 w-full placeholder-white placeholder-opacity-40 text-sm text-gray-300 bg-transparent border-0 border-b-2 border-gray-300 focus:outline-none focus:ring-0 focus:border-orange-300" placeholder={useSelector(state=>state.member.nickname)} />
                    </div>
                    <div className='m-10 text-left'>
                        <p className='text-gray-300'>성별</p>
                        <input type="text" className="block py-2.5 px-0 w-full placeholder-white placeholder-opacity-40 text-sm text-gray-300 bg-transparent border-0 border-b-2 border-gray-300 focus:outline-none focus:ring-0 focus:border-orange-300" placeholder="남/여" />
                    </div>
                    <div className='m-10 text-left'>
                        <p className='text-gray-300'>선호 장르</p>
                        <div className='grid grid-cols-3 gap-3 mt-4'>
                            {getButtons()}
                        </div>
                    </div>
                    <button className='w-5/6 h-10 mb-5 bg-orange-400 rounded-lg' onClick={register}>회원가입</button>
                </div>
            </div>
        </div>
    </div>
  )
}

export default UserRegister