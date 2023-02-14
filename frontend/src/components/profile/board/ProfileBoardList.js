import React from "react";
import { Route, Routes } from "react-router-dom";
import ProfileBoardMyList from "./ProfileBoardMyList";
import ProfileBoardBookmarkList from "./ProfileBoardBookmarkList";
import ProfileBoardNav from "./ProfileBoardNav";

function ProfileBoardList(props) {
  const showTopButton = () => {
    props.showTopButton();
  };
  return (
    <div>
      <ProfileBoardNav />
      <Routes>
        <Route
          path="bookmark"
          element={<ProfileBoardBookmarkList showTopButton={showTopButton} />}
        ></Route>
        <Route
          path="my"
          element={<ProfileBoardMyList showTopButton={showTopButton} />}
        ></Route>
      </Routes>
    </div>
  );
}

export default ProfileBoardList;
