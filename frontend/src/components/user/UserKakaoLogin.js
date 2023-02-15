import axios from "axios";
import React, { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import {
  setAccessToken,
  setGernes1,
  setGernes2,
  setGernes3,
  setImagePath,
  setIsLogin,
  setNickname,
  setRefreshToken,
} from "../../feature/reducer/MemberReducer";

function UserKakaoLogin() {
  const PARAMS = new URL(document.location).searchParams;
  const KAKAO_CODE = PARAMS.get("code");
  const dispatch = useDispatch();
  const movePage = useNavigate();
  const baseURL = process.env.REACT_APP_BASE_URL;

  function goMain() {
    movePage("/");
  }
  function goRegister() {
    movePage("/user/register");
  }

  useEffect(() => {
    async function checkMember() {
      try {
        axios
          .post(
            baseURL + "/auth/member/kakao",
            {},
            {
              headers: {
                authorization_code: KAKAO_CODE,
              },
            }
          )
          .then((res) => {
            if (res.data.status_code === 200) {
              dispatch(setAccessToken(res.data.data.access_token));
              dispatch(setRefreshToken(res.data.data.refresh_token));
              dispatch(setNickname(res.data.data.nickname));
              if(res.data.data.genres) {
                dispatch(setGernes1(res.data.data.genres[0]));
                dispatch(setGernes2(res.data.data.genres[1]));
                dispatch(setGernes3(res.data.data.genres[2]));
              }
              if(res.data.data.imagePath) {
                dispatch(setImagePath(res.data.data.imagePath));
              }
              dispatch(setIsLogin());
              goMain();
            } else if (res.data.status_code === 400) {
              dispatch(setAccessToken(res.data.data.access_token));
              dispatch(setRefreshToken(res.data.data.refresh_token));
              goRegister();
            }
          });
      } catch (e) {
        console.error(e);
      }
    }
    checkMember();
  });
  return (
    <div className="grid content-center h-full grid-cols-3 bg-black kakaoLogin userLogin bg-opacity-60"></div>
  );
}

export default UserKakaoLogin;
