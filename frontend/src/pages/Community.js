import React from "react";
import { Routes, Route } from "react-router-dom";
import CommunityDetail from "../components/community/CommunityDetail";
import CommunityList from "../components/community/CommunityList";
import CommunityWrite from "../components/community/CommunityWrite";

function Community() {
  return (
    <div className="grid h-full px-10 bg-fill bg-community">
      <Routes>
        <Route path="list/*" element={<CommunityList />}></Route>
        <Route path="detail/*" element={<CommunityDetail />}></Route>
        <Route path="write/*" element={<CommunityWrite />}></Route>
      </Routes>
    </div>
  );
}

export default Community;
