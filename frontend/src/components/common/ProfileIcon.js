import React from "react";
import { useNavigate } from "react-router-dom";

import Profile from "../../assets/img/profile.png";

function ProfileIcon() {
  const movePage = useNavigate();
  
    function goLogin(){
      movePage('/user/');
    }
  return (
    <div className="flex justify-end col-span-1">
      <img onClick={goLogin} className="rounded-full w-9 h-9 border-2" src={Profile} alt="" />
    </div>
  );
}

export default ProfileIcon;
