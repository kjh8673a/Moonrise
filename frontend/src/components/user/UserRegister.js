import axios from "axios";
import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { setIsLogin } from "../../feature/reducer/MemberReducer";

function UserRegister() {
  const [likeGenre, setLikeGenre] = useState([]);
  const [gender, setGender] = useState("");
  const [nickname, setNickname] = useState(
    useSelector((state) => state.member.nickname)
  );
  const access_token = useSelector((state) => state.member.accessToken);
  const refresh_token = useSelector((state) => state.member.refreshToken);
  const genreList = [
    "코미디",
    "SF",
    "멜로",
    "액션",
    "범죄",
    "스릴러",
    "전쟁",
    "판타지",
    "스포츠",
  ];
  const movePage = useNavigate();
  const dispatch = useDispatch();
  function goMain() {
    movePage("/");
  }

  const nicknameHandler = (event) => {
    setNickname(event.target.value);
  };
  const checkHandler = (event) => {
    if (!event.target.checked) {
      setLikeGenre(likeGenre.filter((item) => item !== event.target.value));
    } else {
      if (likeGenre.length < 3) {
        setLikeGenre([event.target.value, ...likeGenre]);
        event.target.checked = true;
      } else {
        event.target.checked = false;
      }
    }
  };
  const genderHandler = (event) => {
    setGender(event.target.value);
  };
  const getButtons = () => {
    const buttonList = genreList.map((genre, index) => (
      <div
        key={index}
        className="flex items-center col-span-1 py-2 pl-3 transition-all rounded-lg hover:bg-white hover:bg-opacity-25"
      >
        <input
          id={"check" + index}
          onChange={checkHandler}
          type="checkbox"
          value={genre}
          className="w-5 h-5 transition-all bg-gray-100 border-gray-300 rounded-lg accent-orange-400 "
        />
        <label
          htmlFor={"check" + index}
          className="w-full py-1 ml-2 text-sm font-medium text-white"
        >
          {genre}
        </label>
      </div>
    ));

    return buttonList;
  };
  async function register() {
    const config = {
      headers: {
        "Content-Type": "application/json",
        access_token: access_token,
        refresh_token: refresh_token,
      },
    };
    const requestBody = {
      nickname: nickname,
      genres: likeGenre,
      gender: gender,
    };
    console.log(requestBody);
    axios.post("/auth/member/join", requestBody, config).then((response) => {
      if (response.status === 200) {
        goMain();
        dispatch(setIsLogin(true));
      }
    });
  }
  return (
    <div className="grid content-center h-full grid-cols-3 bg-black UserRegister bg-opacity-60">
      <div className="col-span-3 text-center">
        <p className="m-4 text-4xl text-white">추가 정보 입력</p>
        <div className="grid grid-cols-3">
          <div className="col-span-1 col-start-2 bg-black rounded-lg bg-opacity-60">
            <div className="mx-10 mt-10 mb-5 text-left">
              <p className="text-gray-300">닉네임</p>
              <input
                type="text"
                id="title"
                onChange={nicknameHandler}
                className="block w-full px-0 py-4 text-sm text-gray-300 placeholder-white transition-colors duration-300 bg-transparent border-b-2 border-gray-300 placeholder-opacity-40 focus:outline-none focus:border-orange-300"
                placeholder={useSelector((state) => state.member.nickname)}
              />
            </div>
            <div className="mx-10 my-5 text-left">
              <p className="text-gray-300">성별</p>
              <div className="grid grid-cols-2 mt-4">
                <div className="flex items-center col-span-1 my-4 ml-3 transition-all rounded-lg hover:bg-white hover:bg-opacity-25">
                  <input
                    id="gender-radio-1"
                    onChange={genderHandler}
                    type="radio"
                    value="M"
                    name="gender-radio"
                    className="w-4 h-4 bg-gray-100 border-gray-300 accent-orange-400 "
                  />
                  <label
                    htmlFor="gender-radio-1"
                    className="ml-2 text-sm text-white"
                  >
                    남
                  </label>
                </div>
                <div className="flex items-center col-span-1 my-4 ml-3 transition-all rounded-lg hover:bg-white hover:bg-opacity-25">
                  <input
                    id="gender-radio-2"
                    onChange={genderHandler}
                    type="radio"
                    value="W"
                    name="gender-radio"
                    className="w-4 h-4 bg-gray-100 border-gray-300 accent-orange-400 "
                  />
                  <label
                    htmlFor="gender-radio-2"
                    className="ml-2 text-sm text-white"
                  >
                    여
                  </label>
                </div>
              </div>
            </div>
            <div className="mx-10 my-5 text-left">
              <p className="text-gray-300">선호 장르</p>
              <div className="grid grid-cols-3 gap-3 mt-4">{getButtons()}</div>
            </div>
            <button
              className="w-5/6 h-10 mb-5 bg-orange-400 rounded-lg"
              onClick={register}
            >
              회원가입
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default UserRegister;
