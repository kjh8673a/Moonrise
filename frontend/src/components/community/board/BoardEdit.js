import { Editor } from "@toast-ui/react-editor";

import React, { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useSelector } from "react-redux";

function BoardEdit(props) {
  const [title, setTitle] = useState(props.title)
  const movePage = useNavigate();
  const editorRef = useRef();
  const baseURL = process.env.REACT_APP_BASE_URL;
  const access_token = useSelector((state) => state.member.accessToken);
  const config = {
    headers: {
      access_token: access_token,
    },
  };
  const [requestBody, setRequestBody] = useState({
    title: "",
    boardId: props.boardId,
    content: "",
  });

  const goBack = () => {
    movePage("/community/list/");
  };

  useEffect(() => {
    const htmlString = props.content;

    editorRef.current?.getInstance().setHTML(htmlString);
  }, [props.content]);

  const titleChangeHandler = (event) => {
    setRequestBody((prevState) => {
      return { ...prevState, title: event.target.value };
    });
    setTitle(event.target.value)
  };

  const contentChangeHandler = () => {
    setRequestBody((prevState) => {
      return {
        ...prevState,
        content: editorRef.current?.getInstance().getHTML(),
      };
    });
  };

  async function writeBoard() {
    console.log(requestBody.content);
    const res = await axios.put(
      baseURL + "/api/board/modify",
      requestBody,
      config
    );
    console.log(res);
    goBack();
  }

  return (
    <div className="mx-64 mt-5 text-white">
      <div className="grid grid-cols-3 py-3">
        <span
          className="table-column col-span-1 align-bottom cursor-pointer"
          onClick={props.closeEdit}
        >
          &lt; 이전으로
        </span>
        <span className="col-span-1 text-2xl text-center">게시글 수정</span>
      </div>
      <div className="p-2 bg-gray-600 rounded-lg bg-opacity-30">
        <div className="grid grid-cols-12 px-4">
          <div className="col-span-1 mt-3 text-lg">제목 :</div>
          <div className="col-span-11">
            <input
              type="text"
              id="title"
              onChange={titleChangeHandler}
              className="w-full py-3 text-sm text-gray-300 bg-transparent border-0 border-b-2 border-gray-300 focus:outline-none focus:ring-0 focus:border-orange-300"
              placeholder=""
              value={title}
            />
          </div>
        </div>
        <div className="mx-4 my-2">
          <Editor
            ref={editorRef}
            placeholder="내용을 입력해주세요."
            previewStyle="vertical" // 미리보기 스타일 지정
            height="500px" // 에디터 창 높이
            initialEditType="wysiwyg" // 초기 입력모드 설정(디폴트 markdown)
            onChange={contentChangeHandler}
            toolbarItems={[
              // 툴바 옵션 설정
              ["heading", "bold", "italic", "strike"],
              ["hr", "quote"],
              ["ul", "ol", "task", "indent", "outdent"],
              ["table", "image", "link"],
              ["code", "codeblock"],
            ]}
            hooks={{
              addImageBlobHook: async (blob, callback) => {
                console.log(blob); // File {name: '카레유.png', ... }

                // 1. 첨부된 이미지 파일을 서버로 전송후, 이미지 경로 url을 받아온다.
                // const imgUrl = await .... 서버 전송 / 경로 수신 코드 ...

                const formData = new FormData();
                formData.append("files", blob);

                // api/image/upload , post, data form {files : }
                const response = await axios.post(
                  baseURL + "/api/image/upload",
                  formData,
                  {
                    headers: {
                      "Content-Type": "multipart/form-data",
                    },
                  }
                );
                console.log(response.data.data[0]);

                // 2. 첨부된 이미지를 화면에 표시(경로는 임의로 넣었다.)
                callback(response.data.data[0], "업로드 이미지");
              },
            }}
          ></Editor>
        </div>
        <div className="text-right">
          <button
            onClick={props.closeEdit}
            className="px-4 py-2 mr-4 bg-white rounded-lg bg-opacity-10 hover:bg-opacity-30 hover:text-white"
          >
            취소
          </button>
          <button
            onClick={writeBoard}
            className="px-4 py-2 mr-4 bg-white rounded-lg bg-opacity-10 hover:bg-opacity-30 hover:text-white"
          >
            게시글 수정
          </button>
        </div>
      </div>
    </div>
  );
}

export default BoardEdit;
