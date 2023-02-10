import React, { useEffect, useState } from "react";
import BoardCard from "./BoardCard";
import CommunityHeader from "../CommunityHeader";
import axios from "axios";
import { useSelector } from "react-redux";

function BoardList() {
  const [boards, setBoards] = useState([]);
  const [page, setPage] = useState(0);
  const movieId = useSelector((state) => state.movie.movieId);

  useEffect(() => {
    axios
      .get("http://3.35.149.202:80/api/board/list/" + movieId + "?page=" + page)
      .then((response) => {
        setBoards(response.data.data.findBoards);
        setPage(0); // 임시
      });
  }, [movieId, page]);

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
            write_date={board.dateTime.replace("T", " ")}
            nickname={board.writer}
            poster={
              "https://images.ctfassets.net/usf1vwtuqyxm/6Meesa1ONupgjIy7JS5TvF/10fff5e089662ef26336c1e2b8414f70/minalima-weasley-family.jpg?w=315&h=315&fit=fill&f=top&fm=webp&q=70"
            }
            like_cnt={board.likeCnt}
            view_cnt={board.viewCnt}
          />
        ))}
      </ul>
    </div>
  );
}

export default BoardList;
