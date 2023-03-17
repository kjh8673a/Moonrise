import axios from "axios";
import React, { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import {
  setAccessToken,
  setIsLogin,
  setNickname,
  setRefreshToken,
} from "../../feature/reducer/MemberReducer";

function UserKakaoLogin() {
  const PARAMS = new URL(document.location).searchParams;
  const KAKAO_CODE = PARAMS.get("code");
  const dispatch = useDispatch();
  const movePage = useNavigate();
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
            "/auth/member/kakao",
            {},
            {
              headers: {
                authorization_code: KAKAO_CODE,
              },
            }
          )
          .then((res) => {
            console.log(res);
            if (res.data.status_code === 200) {
              dispatch(setAccessToken(res.data.data.access_token));
              dispatch(setRefreshToken(res.data.data.refresh_token));
              dispatch(setNickname(res.data.data.nickname));
              dispatch(setIsLogin());
              goMain();
            } else if (res.data.status_code === 400) {
              dispatch(setAccessToken(res.data.data.access_token));
              dispatch(setRefreshToken(res.data.data.refresh_token));
              dispatch(setNickname(res.data.data.nickname));
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
