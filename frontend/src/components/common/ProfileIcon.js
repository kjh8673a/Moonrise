import React from "react";

import Profile from "../../assets/img/profile.png";

function ProfileIcon() {
  return (
    <div className="flex justify-end col-span-1">
      <img className="rounded-full w-9 h-9 border-2" src={Profile} alt="" />
    </div>
  );
}

export default ProfileIcon;
