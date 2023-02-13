import { Editor } from "@toast-ui/react-editor";
import '@toast-ui/editor/dist/toastui-editor.css';

import axios from "axios";
import React, { useRef } from "react";
import { useNavigate } from "react-router-dom";

function BoardWrite() {
  const movePage = useNavigate();
  const editorRef = useRef();

  const goBack = () => {
    movePage("/community/list/");
  };
  function writeBoard(){
    console.log(editorRef.current?.getInstance().getHTML());
  }

  return (
    <div className="mx-64 mt-5 text-white">
      <div className="grid grid-cols-3 py-3">
        <span
          className="cursor-pointer col-span-1 table-column align-bottom"
          onClick={goBack}
        >
          &lt; 이전으로
        </span>
        <span className="col-span-1 text-center text-2xl">
          새로운 게시글 작성
        </span>
      </div>
      <div className="bg-gray-600 bg-opacity-30 rounded-lg p-2">
        <div className="px-4 grid grid-cols-12">
          <div className="col-span-1 mt-3 text-lg">
            제목 :
          </div>
          <div className="col-span-11">
            <input
              type="text"
              id="title"
              class="py-3 w-full text-sm text-gray-300 bg-transparent border-0 border-b-2 border-gray-300 focus:outline-none focus:ring-0 focus:border-orange-300"
              placeholder=""
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
          toolbarItems={[
            // 툴바 옵션 설정
            ['heading', 'bold', 'italic', 'strike'],
            ['hr', 'quote'],
            ['ul', 'ol', 'task', 'indent', 'outdent'],
            ['table', 'image', 'link'],
            ['code', 'codeblock']
          ]}
          hooks={{
            addImageBlobHook: async (blob, callback) => {
              
              console.log(blob);  // File {name: '카레유.png', ... }
  
              // 1. 첨부된 이미지 파일을 서버로 전송후, 이미지 경로 url을 받아온다.
              // const imgUrl = await .... 서버 전송 / 경로 수신 코드 ...
  
              // 2. 첨부된 이미지를 화면에 표시(경로는 임의로 넣었다.)
              callback('http://localhost:5000/img/카레유.png', '카레유');
            }
          }}
        ></Editor>
        </div>
        <div className="text-right">
          <button onClick={writeBoard} className="rounded-lg mr-4 px-4 py-2 bg-white bg-opacity-10 hover:bg-opacity-30 hover:text-white">
            게시글 등록
          </button>
        </div>
      </div>
    </div>
  );
}

export default BoardWrite;
