import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import Profile from "../../assets/img/profile.png";

function ProfileIcon() {
  const movePage = useNavigate();
  
  const goMyPage = () => {
    movePage('/profile/list');
  }
  
  function goLogin(){
    movePage('/user/');
  }
  
  const [isHovering, setIsHovering] = useState(false);

  const mouseEnterHandler = () => {
    setIsHovering(true)
  }
  const mouseLeaveHandler = () => {
    setIsHovering(false)
  }

  return (
    <div className="flex justify-end col-span-1">
      <div className="text-left" onMouseEnter={mouseEnterHandler} onMouseLeave={mouseLeaveHandler}>
        {!isHovering && <img  className="border-2 rounded-full w-9 h-9" src={Profile} alt="" />}
        {isHovering && (<div className="w-56 mt-2 origin-top-right bg-white rounded-md shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none" role="menu" aria-orientation="vertical" aria-labelledby="menu-button" tabindex="-1">
          <div className="py-1 divide-y" role="none">
            <div className="flex p-3">
              <img  className="w-12 h-12 border-2 rounded-full" src={Profile} alt="" />
              <div>
                <p className="mx-4 text-lg">
                  최현인
                </p>
                <p className="mx-4 text-xs">
                  #SF #로맨스 #판타지
                </p>
              </div>
            </div>
            <button onClick={goMyPage} className="block w-full px-4 py-2 text-sm text-left text-gray-700" >마이페이지</button>
            <button onClick={goLogin} className="block w-full px-4 py-2 text-sm text-left text-gray-700" >로그인</button>
            <button className="block w-full px-4 py-2 text-sm text-left text-gray-700" >로그아웃</button>
          </div>
        </div>) }
        
      </div>
    </div>
  );
}

export default ProfileIcon;
