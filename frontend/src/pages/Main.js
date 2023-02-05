import React from "react";
import Logo from "../components/common/Logo";
import ProfileIcon from "../components/common/ProfileIcon";
import MainSearch from "../components/main/MainSearch";

function Main() {
  return (
    <>
      <div className="grid grid-cols-3 ">
        <div className="relative col-span-1 bg-white h-screen p-8">
          <Logo color={"315B4C"}/>
        </div>
        <div className="main col-span-2 pb-0 mb-0 bg-main bg-cover p-8">
          <ProfileIcon />
        </div>
      </div>
      <div className="fixed z-60 top-40 left-64 bg-green-800 h-96 w-2/5">
        <MainSearch />
      </div>
    </>
  );
}

export default Main;
