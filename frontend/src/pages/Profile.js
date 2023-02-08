import React from "react";
import { Route, Routes } from "react-router-dom";
import ProfileList from "../components/profile/ProfileList";

function Profile() {
  return (
    <div className="grid h-full px-10 bg-fill bg-community">
      <Routes>
        <Route path="list/*" element={<ProfileList />}></Route>
      </Routes>
    </div>
  );
}

export default Profile;
