import axios from "axios";
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import Profile from "../../assets/img/profile.png";
import { logoutUser } from "../../feature/reducer/MemberReducer";

function ProfileIcon() {
  const [isLogin, setIsLogin] = useState(
    useSelector((state) => state.member.isLogin)
  );
  const dispatch = useDispatch();
  const [isHovering, setIsHovering] = useState(false);
  const movePage = useNavigate();

  const goMyPage = () => {
    movePage("/profile/list");
  };

  function goLogin() {
    movePage("/user/");
  }

  const access_token = useSelector((state) => state.member.accessToken);
  const config = {
    headers: {
      access_token: access_token,
    },
  };

  function logout() {
    axios
      .get("http://3.35.149.202:80/auth/member/logout", config)
      .then((res) => console.log(res));
    logoutUser();
    dispatch(logoutUser());
    setIsLogin(false);
  }

  const mouseEnterHandler = () => {
    setIsHovering(true);
  };
  const mouseLeaveHandler = () => {
    setIsHovering(false);
  };

  const nicknameValue = useState(useSelector((state) => state.member.nickname));
  const gerne1 = useState(useSelector((state) => state.member.gernes1));
  const gerne2 = useState(useSelector((state) => state.member.gernes2));
  const gerne3 = useState(useSelector((state) => state.member.gernes3));
  const imagePath = useState(useSelector((state) => state.member.imagePath));
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
          {!isHovering && imagePath && (
            <img
              className="border-2 rounded-full w-9 h-9"
              src={imagePath[0]}
              alt=""
            />
          )}
          {!isHovering && !imagePath && (
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
            >
              <div className="py-1 divide-y" role="none">
                <div className="flex p-3">
                  {imagePath && (
                    <img
                      className="w-12 h-12 border-2 rounded-full"
                      src={imagePath[0]}
                      alt=""
                    />
                  )}
                  {!imagePath && (
                    <img
                      className="w-12 h-12 border-2 rounded-full"
                      src={Profile}
                      alt=""
                    />
                  )}
                  <div>
                    <p className="mx-4 text-lg">{nicknameValue}</p>
                    <p className="mx-4 text-xs">
                      {gerne1 && (
                        <span>
                          #{gerne1} #{gerne2} #{gerne3}
                        </span>
                      )}
                    </p>
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
