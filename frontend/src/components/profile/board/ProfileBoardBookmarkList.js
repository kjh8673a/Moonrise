import axios from "axios";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import ProfileBoardCard from "../board/ProfileBoardCard";
import ProfileSeeMoreButton from "../ProfileSeeMoreButton";

function ProfileBoardBookmarkList() {
  const [data, setData] = useState([]);
  const [limit, setLimit] = useState(8);
  const [list, setList] = useState([]);

  const access_token = useSelector((state) => state.member.accessToken);

  useEffect(() => {
    const config = {
      headers: {
        access_token: access_token,
      },
    };
    axios
      .get("http://3.35.149.202:80/api/board/mypage/bookmark", config)
      .then((response) => { 
        if(!response.data.data.findBoards) {
          setData([]);
        }else {
          setData(response.data.data.findBoards);
        }
      });
  }, [access_token]);

  useEffect(() => {
    if(data.length > 0) {
      setList(data.filter((item, index) => index < limit));
    }
  }, [data, limit]);

  const seeMore = () => {
    setLimit(limit + 8);
  };

  return (
    <div>
      <div className="p-2">
        <ul className="grid grid-cols-4 gap-4 justify-items-center">
          {list.map((board) => (
            <ProfileBoardCard
              board_id={board.boardId}
              title={board.title}
              movie={board.movieTitle}
              write_date={board.dateTime.replace("T", " ")}
              image={board.image}
            />
          ))}
        </ul>
        {limit < data.length && <ProfileSeeMoreButton seeMore={seeMore} />}
      </div>
    </div>
  );
}

export default ProfileBoardBookmarkList;
