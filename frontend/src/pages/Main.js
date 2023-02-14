import React from "react";
import Logo from "../components/common/Logo";
import ProfileIcon from "../components/common/ProfileIcon";
import MainSearch from "../components/main/MainSearch";

function Main() {
  return (
    <>
      <div className="grid grid-cols-3 ">
        <div className="relative h-screen col-span-1 p-8 bg-white">
          <Logo color={"315B4C"}/>
        </div>
        <div className="col-span-2 p-8 pb-0 mb-0 bg-cover main bg-main">
          <ProfileIcon />
        </div>
      </div>
      <div className="fixed w-2/5 shadow-xl z-60 top-40 left-64 bg-dal-green h-96">
        <MainSearch />
      </div>
    </>
  );
}

export default Main;
