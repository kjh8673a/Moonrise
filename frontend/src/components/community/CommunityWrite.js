import React from "react";
import { Route, Routes } from "react-router-dom";
import PartyWrite from "./party/PartyWrite";
import BoardWrite from "./board/BoardWrite";
import TalkWrite from "./talk/TalkWrite";

function CommunityWrite() {
  return (
    <div className="communityWrite">
      <Routes>
        <Route path="party" element={<PartyWrite />}></Route>
        <Route path="talk" element={<TalkWrite />}></Route>
        <Route path="board" element={<BoardWrite />}></Route>
      </Routes>
    </div>
  );
}

export default CommunityWrite;
