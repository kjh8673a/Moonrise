import React from "react";
import { useNavigate } from "react-router-dom";

import Profile from "../../assets/img/profile.png";

function ProfileIcon() {
  const movePage = useNavigate();
  
  const goMyPage = () => {
    movePage('/profile/list');
  }

  return (
    <div className="flex justify-end col-span-1">
      <img className="border-2 rounded-full w-9 h-9" src={Profile} alt="" onClick={goMyPage}/>
    </div>
  );
}

export default ProfileIcon;
