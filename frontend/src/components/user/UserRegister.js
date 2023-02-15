import axios from 'axios';
import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { setNickname, setGernes1, setGernes2, setGernes3, setImagePath, setIsLogin } from '../../feature/reducer/MemberReducer';



function UserRegister() {
    const [likeGenre, setLikeGenre] = useState([]);
    const [gender, setGender] = useState("M");
    const [genderPick, setGenderPick] = useState(true);
    const [inputNickname, setInputNickname] = useState("");
    const access_token = useSelector(state=> state.member.accessToken);
    const refresh_token = useSelector(state=> state.member.refreshToken);
    const [step, setStep] = useState(1);
    const [genreCheck, setGenreCheck] = useState([false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false]);
    const genreList = ["SF", "가족","공포", "다큐멘터리",
    "드라마", "로맨스", "모험", "미스터리",
    "범죄", "서부", "스릴러", "애니메이션",
    "액션", "역사", "음악", "전쟁",
    "코미디", "판타지"];
    const baseURL = process.env.REACT_APP_BASE_URL;
    const movePage = useNavigate(); 
    const dispatch = useDispatch();
    function goMain(){
        movePage('/');
      }  

    const nicknameHandler = (event) => {
        setInputNickname(event.target.value);
    }
    const checkHandler = index => event => {
        if (!event.target.checked) {
            setLikeGenre(likeGenre.filter(item => item !== event.target.value))
            setGenreCheck(existingItems => {
                return [
                  ...existingItems.slice(0, index),
                  false,
                  ...existingItems.slice(index+ 1, 18),
                ]
              })
        }
        else{
            if (likeGenre.length < 3) {
                setLikeGenre([event.target.value, ...likeGenre]);
                setGenreCheck(existingItems => {
                    return [
                      ...existingItems.slice(0, index),
                      true,
                      ...existingItems.slice(index + 1, 18),
                    ]
                  })
            }
        }
    }
    const genderHandler = (event) => {
        setGender(event.target.value);
        if (event.target.value === "M") {
            setGenderPick(true)
        }
        else{
            setGenderPick(false)
        }
    }
    const getButtons = () => {
        const buttonList = genreList.map((genre, index) => (
        <div key={index} className="relative flex items-center col-span-1 transition-all rounded-lg hover:bg-white hover:bg-opacity-25">
            <input onChange={checkHandler(index)} type="checkbox" value={genre} checked={genreCheck[index]} className="w-5 h-5 transition-all bg-gray-100 border-gray-300 rounded-lg accent-dal-orange "/>
            <label htmlFor={"check"+index} className="w-full py-1 ml-2 text-sm font-medium text-white">{genre}</label>
        </div>
        ))
        
        return (
            buttonList
        );
    }
  function next() {
    setStep(2);
  }
  function before(){
    setStep(1);
  }
  function register(){
      const config = { 
          headers: {
            "Content-Type": "application/json",
            access_token: access_token,
            refresh_token: refresh_token,
            }
          }
      const requestBody = {
        nickname: inputNickname,
        genres: likeGenre,
        gender : gender,
      }
      axios.post(baseURL + '/auth/member/join', requestBody, config)
      .then(response => {
        if (response.status === 200) {
            dispatch(setNickname(response.data.data.nickname))
            dispatch(setImagePath(response.data.data.imagePath))
            dispatch(setGernes1(response.data.data.genres[0]))
            dispatch(setGernes2(response.data.data.genres[1]))
            dispatch(setGernes3(response.data.data.genres[2]))
            dispatch(setIsLogin(true));
            goMain();
        }
      })
        
  }
  return (
    <div className='content-center h-screen py-10 bg-black UserRegister bg-opacity-60'>
        <p className='my-10 text-4xl text-center text-white'>추가 정보 입력</p>
        <div className='grid grid-cols-4'>
            <div className="col-span-2 col-start-2 rounded-lg bg-dal-beige bg-opacity-20">
                {step === 1 && (
                    <div>
                        <div className='m-10 text-left'>
                            <p className='text-gray-300'>닉네임</p>
                            <input type="text" id="title" onChange={nicknameHandler} className="block w-full px-0 py-4 text-sm text-gray-300 placeholder-white transition-colors duration-300 bg-transparent border-b-2 border-gray-300 placeholder-opacity-40 focus:outline-none focus:border-orange-300" placeholder="닉네임을 입력해주세요" value={inputNickname}/>
                        </div>
                        <div className='m-10 text-left'>
                            <p className='text-gray-300'>성별</p>
                            <div className='grid grid-cols-2 mt-4'>
                                <div className="flex items-center col-span-1 my-4 ml-3 transition-all rounded-lg">
                                    <input id="gender-radio-1" onChange={genderHandler} type="radio" value="M" name="gender-radio" className="bg-gray-100 border-gray-300 w-7 h-7 accent-dal-orange " checked={genderPick}/>
                                    <label htmlFor="gender-radio-1" className="ml-2 text-lg text-white">남</label>
                                </div>
                                <div className="flex items-center col-span-1 my-4 ml-3 transition-all rounded-lg">
                                    <input id="gender-radio-2" onChange={genderHandler} type="radio" value="W" name="gender-radio" className="bg-gray-100 border-gray-300 w-7 h-7 accent-dal-orange " checked={!genderPick}/>
                                    <label htmlFor="gender-radio-2" className="ml-2 text-lg text-white">여</label>
                                </div>
                            </div>
                        </div>
                        <div className='m-10 text-center'>
                            <button className='w-full h-10 rounded-lg bg-dal-orange disabled:bg-opacity-50' disabled={inputNickname === "" || gender === "" } onClick={next}>다음</button>
                        </div>
                    </div>

                )}
                {step === 2 && (
                    <div>
                        <div className='mx-10 my-10 text-left'>
                            <button className='px-2 text-white bg-transparent rounded-full hover:bg-white hover:bg-opacity-10' onClick={before}>&lt; 이전으로</button>
                            <p className='px-2 mt-4 text-gray-300'>선호 장르 (최대 3개 선택 가능)</p>
                            <div className='grid grid-cols-4 gap-3 mt-4'>
                                {getButtons()}
                            </div>
                        </div>
                        <div className='px-10 my-10'>
                            <button className='w-full h-10 rounded-lg bg-dal-orange' onClick={register}>회원가입</button>
                        </div>
                    </div>
                )}
            </div>
        </div>
    </div>
  )
}

export default UserRegister