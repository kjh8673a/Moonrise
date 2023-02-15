import axios from "axios";
import React, { useState, Fragment } from "react";
import { Dialog, Transition } from "@headlessui/react";
import { useSelector } from "react-redux";
import TalkRoom from "./TalkRoom";

function TalkCard(props) {
  const [onModal, setOnModal] = useState(false);
  const [enterTalk, setEnterTalk] = useState(false);
  const [talkDetail, setTalkDetail] = useState({});
  const [talkDate, setTalkDate] = useState("");
  const [ppl , setPpl] = useState("");
  const access_token = useSelector(state => state.member.accessToken);
  const baseURL = process.env.REACT_APP_BASE_URL;
  const config = { 
    headers: {
      "access_token": access_token,
    }
  };
  
  function modalHandler() {
    axios.get(baseURL + "/api/debate/read/"+props.talkInfo.debateId, config)
    .then(response => {
      setTalkDetail(response.data.data.readDebate);
      setTalkDate(response.data.data.readDebate.createDate);
      setPpl(response.data.data.readDebate.nowppl+"/"+response.data.data.readDebate.maxppl)
    }).then(setOnModal(true));
  }
  function modalCloseHandler(){
    setOnModal(false)
    setEnterTalk(false)
  }  
  async function enterHandler(){
    const response = await axios.get(baseURL + "/chat/debate/check?debateId=" + talkDetail.debateId);
    if (response.data.data.isEnter) {
      setEnterTalk(true)
    }
    else{
      alert("채팅방 정원 초과 입니다.")
    }
    
  }
  return (
    <div className="col-span-1 overflow-auto">
      <div
        className="h-24 px-10 py-2 text-center text-white transition-all bg-white rounded-lg cursor-pointer bg-opacity-5 group hover:bg-opacity-20 hover:bg-orange-200"
        onClick={modalHandler}>
        <div className="overflow-hidden text-lg transition-all duration-200 group-hover:text-xl mb-7 text-ellipsis whitespace-nowrap">
          {props.talkInfo.title}
        </div>
        <div className="">{props.talkInfo.writer}</div>
      </div>
      <Transition appear show={onModal} as={Fragment}>
        <Dialog as="div" className="relative z-10" onClose={modalCloseHandler}>
          <Transition.Child
            as={Fragment}
            enter="ease-out duration-300"
            enterFrom="opacity-0 "
            enterTo="opacity-100 "
            leave="ease-in duration-200"
            leaveFrom="opacity-100"
            leaveTo="opacity-0"
          >
            <div className="fixed inset-0 bg-black bg-opacity-25" />
          </Transition.Child>

          <div className="fixed inset-0 overflow-y-auto">
            <div className="flex items-center justify-center min-h-full p-4 text-center">
              <Transition.Child
                as={Fragment}
                enter="ease-out duration-300"
                enterFrom="opacity-0 transform translate-y-10 scale-60"
                enterTo="opacity-100 transform translate-y-0 scale-100"
                leave="ease-in duration-200"
                leaveFrom="opacity-100 scale-100"
                leaveTo="opacity-0 scale-95"
              >
                <Dialog.Panel className="w-full max-w-md p-4 overflow-hidden text-left align-middle transition-all transform bg-white shadow-xl rounded-2xl">
                  <div className="grid grid-cols-6">
                    <div className="col-span-5 mt-2">
                      <Dialog.Title
                        as="h3"
                        className="text-xl font-bold leading-6 text-gray-900"
                      >
                        {talkDetail.title}
                      </Dialog.Title>
                    </div>
                    <div className="col-span-1 text-right">
                      <button onClick={modalCloseHandler}>
                        X
                      </button>
                    </div>
                  </div>

                  <div className="grid grid-cols-2 mt-2">
                    <div className="col-span-1">
                      <p className="mt-1 text-sm">
                        담소 시작일 : {talkDate.substring(0,4)}년 {talkDate.substring(5,7)}월 {talkDate.substring(8,10)}일
                      </p>
                    </div>
                    <div className="col-span-1 text-right">
                      <p>
                        {ppl}
                      </p>
                    </div>
                  </div>
                  <hr className="my-2"></hr>
                  {!enterTalk && (
                    <div>
                      <p className="my-10">
                        {talkDetail.description}
                      </p>
                      <hr className="my-2"></hr>
                      <div className="mx-4 my-2 text-center">
                        <button onClick={enterHandler} className="px-4 py-2 mt-4 transition-all rounded-lg bg-dal-orange hover:bg-opacity-60">
                          담소 참여하기
                        </button>
                      </div>
                    </div>
                  )}
                  {enterTalk && (
                    <TalkRoom talkDetail={talkDetail}/>                
                  )}
                </Dialog.Panel>
              </Transition.Child>
            </div>
          </div>
        </Dialog>
      </Transition>
    </div>
      
  );
}

export default TalkCard;
