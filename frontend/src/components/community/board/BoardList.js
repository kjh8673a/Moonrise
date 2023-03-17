import React, { useEffect, useState } from "react";
import BoardCard from "./BoardCard";
import CommunityHeader from "../CommunityHeader";
import axios from "axios";
import { useSelector } from "react-redux";

function BoardList() {
  const [boards, setBoards] = useState([]);
  const movieId = useSelector((state) => state.movie.movieId);

  useEffect(() => {
    axios.get("/api/board/list/" + movieId).then((response) => {
      setBoards(response.data.findBoards);
    });
  });

  return (
    <div>
      <CommunityHeader type="게시글" />
      <ul>
        {boards.map((board) => (
          <BoardCard
            key={board.id}
            id={board.id}
            title={board.title}
            content={board.content}
            write_date={board.dateTime}
            nickname={board.writer}
            poster={
              "https://images.ctfassets.net/usf1vwtuqyxm/6Meesa1ONupgjIy7JS5TvF/10fff5e089662ef26336c1e2b8414f70/minalima-weasley-family.jpg?w=315&h=315&fit=fill&f=top&fm=webp&q=70"
            }
            like_cnt={0}
            comment_cnt={0}
          />
        ))}
      </ul>
    </div>
  );
}

export default BoardList;
