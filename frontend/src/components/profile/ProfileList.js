import React, { useRef, useState } from "react";
import { Route, Routes } from "react-router-dom";
import MainNav from "../common/MainNav";
import ProfileCard from "./profile/ProfileCard";
import ProfileNav from "./ProfileNav";
import ProfileLogList from "./log/ProfileLogList";
import ProfileBoardList from "./board/ProfileBoardList";
import ProfilePartyList from "./party/ProfilePartyList";
import ProfileEditorCard from "./profile/profileEditorCard";
import ProfileScrollTopButton from "./ProfileScrollTopButton";

function ProfileList() {
  const [onEdit, setOnEdit] = useState(false);
  const scrollRef = useRef();

  const openEditor = () => {
    setOnEdit(true);
  };

  const closeEditor = () => {
    setOnEdit(false);
  };

  const scrollToTop = () => {
    scrollRef.current.scrollIntoView({ behavior: "smooth", block: "start" });
  };

  return (
    <div>
      <MainNav />
      <div className="grid grid-rows-6 h-[85vh]">
        <div className="row-span-1"></div>
        <div className="grid grid-cols-3 row-span-5 bg-[#315B4C] bg-opacity-80 overflow-auto">
          <div className="col-span-1"></div>
          <div className="col-span-2 p-5" ref={scrollRef}>
            <ProfileNav />
            <Routes>
              <Route path="" element={<ProfileLogList />}></Route>
              <Route path="board/*" element={<ProfileBoardList />}></Route>
              <Route path="party/*" element={<ProfilePartyList />}></Route>
            </Routes>
            <ProfileScrollTopButton scrollToTop={scrollToTop} />
          </div>
        </div>
      </div>
      {!onEdit && (
        <div className="fixed w-1/5 bg-white top-40 left-40 h-3/4 rounded-md">
          <ProfileCard openEditor={openEditor} />
        </div>
      )}
      {onEdit && (
        <div className="fixed w-1/2 bg-white top-40 left-40 h-3/4 rounded-md">
          <ProfileEditorCard closeEditor={closeEditor} />
        </div>
      )}
    </div>
  );
}

export default ProfileList;
