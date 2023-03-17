import axios from "axios";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import Profile from "../../assets/img/profile.png";
import { logoutUser } from "../../feature/reducer/MemberReducer";

function ProfileIcon() {
  const [isLogin, setIsLogin] = useState(
    useSelector((state) => state.member.isLogin)
  );
  const [isHovering, setIsHovering] = useState(false);
  const movePage = useNavigate();

  const goMyPage = () => {
    movePage("/profile/list");
  };

  function goLogin() {
    movePage("/user/");
  }

  function logout() {
    axios.get("/auth/member/logout").then((res) => console.log(res));
    logoutUser();
    setIsLogin(false);
  }

  const mouseEnterHandler = () => {
    setIsHovering(true);
  };
  const mouseLeaveHandler = () => {
    setIsHovering(false);
  };
  useEffect(() => {
    console.log("로그인 상태 변경");
  }, [isLogin]);
  if (isLogin) {
    return (
      <div className="relative flex justify-end col-span-1">
        <div
          className="text-left"
          onMouseEnter={mouseEnterHandler}
          onMouseLeave={mouseLeaveHandler}
        >
          {!isHovering && (
            <img
              className="border-2 rounded-full w-9 h-9"
              src={Profile}
              alt=""
            />
          )}
          {isHovering && (
            <div
              className="absolute top-0 right-0 z-10 w-56 mt-2 origin-top-right bg-white rounded-md shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none"
              role="menu"
              aria-orientation="vertical"
              aria-labelledby="menu-button"
              tabindex="-1"
            >
              <div className="py-1 divide-y" role="none">
                <div className="flex p-3">
                  <img
                    className="w-12 h-12 border-2 rounded-full"
                    src={Profile}
                    alt=""
                  />
                  <div>
                    <p className="mx-4 text-lg">최현인</p>
                    <p className="mx-4 text-xs">#SF #로맨스 #판타지</p>
                  </div>
                </div>
                <button
                  onClick={goMyPage}
                  className="block w-full px-4 py-2 text-sm text-left text-gray-700"
                >
                  마이페이지
                </button>
                <button
                  onClick={logout}
                  className="block w-full px-4 py-2 text-sm text-left text-gray-700"
                >
                  로그아웃
                </button>
              </div>
            </div>
          )}
        </div>
      </div>
    );
  } else {
    return (
      <div className="relative flex justify-end col-span-1">
        <div
          className="text-left"
          onMouseEnter={mouseEnterHandler}
          onMouseLeave={mouseLeaveHandler}
        >
          {!isHovering && (
            <img
              className="border-2 rounded-full w-9 h-9"
              src={Profile}
              alt=""
            />
          )}
          {isHovering && (
            <div
              className="absolute top-0 right-0 z-10 w-56 mt-2 bg-white rounded-md shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none"
              role="menu"
              aria-orientation="vertical"
              aria-labelledby="menu-button"
              tabindex="-1"
            >
              <div className="py-1 divide-y" role="none">
                <div className="flex p-3">
                  <div onClick={goLogin}>
                    <p className="mx-4 text-lg">로그인</p>
                    <p className="mx-4 text-xs">어서오세요 달뜸입니다.</p>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    );
  }
}

export default ProfileIcon;
