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
import { useSelector } from "react-redux";

function ProfileList() {
  const [onEdit, setOnEdit] = useState(false);
  const scrollRef = useRef();
  const nicknameValue = useState(useSelector((state) => state.member.nickname));
  const gerne1 = useState(useSelector((state) => state.member.gernes1));
  const gerne2 = useState(useSelector((state) => state.member.gernes2));
  const gerne3 = useState(useSelector((state) => state.member.gernes3));
  const imagePath = useState(useSelector((state) => state.member.imagePath));

  const openEditor = () => {
    setOnEdit(true);
  };

  const closeEditor = () => {
    setOnEdit(false);
  };

  const scrollToTop = () => {
    scrollRef.current.scrollIntoView({ behavior: "smooth", block: "start" });
  };

  const editDone = () => {
    window.location.reload();
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
        <div className="fixed w-1/5 bg-white rounded-md top-40 left-40 h-3/4">
          <ProfileCard
            openEditor={openEditor}
            nicknameValue={nicknameValue}
            gerne1={gerne1}
            gerne2={gerne2}
            gerne3={gerne3}
            imagePath={imagePath[0]}
          />
        </div>
      )}
      {onEdit && (
        <>
          <div
            className="fixed top-0 left-0 z-20 w-full h-full bg-black opacity-70"
            onClick={closeEditor}
          ></div>
          <div className="fixed z-50 w-1/2 bg-white rounded-md top-40 left-40 h-3/4">
            <ProfileEditorCard
              editDone={editDone}
              closeEditor={closeEditor}
              nicknameValue={nicknameValue}
              gerne1={gerne1}
              gerne2={gerne2}
              gerne3={gerne3}
              imagePath={imagePath[0]}
            />
          </div>
        </>
      )}
    </div>
  );
}

export default ProfileList;
