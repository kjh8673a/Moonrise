import axios from "axios";
import React, { useState, Fragment } from "react";
import { Dialog, Transition } from "@headlessui/react";
import { useSelector } from "react-redux";
import TalkRoom from "./TalkRoom";

function TalkCard(props) {
  const [onModal, setOnModal] = useState(false);
  const [enterTalk, setEnterTalk] = useState(false);
  const [talkDetail, setTalkDetail] = useState({})
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
      console.log(response)
      setTalkDetail(response.data.data.readDebate);
      setPpl(response.data.data.readDebate.nowppl+"/"+response.data.data.readDebate.maxppl)
    }).then(setOnModal(true));
  }
  function modalCloseHandler(){
    setOnModal(false)
    setEnterTalk(false)
  }  
  function enterHandler(){
    setEnterTalk(true)
    
  }
  return (
    <div className="col-span-1 overflow-auto">
      <div
        className="px-10 py-2 h-24 bg-white transition-all bg-opacity-5 text-center cursor-pointer rounded-lg group text-white hover:bg-opacity-20 hover:bg-orange-200"
        onClick={modalHandler}>
        <div className="overflow-hidden transition-all duration-200 text-lg group-hover:text-xl mb-7 text-ellipsis whitespace-nowrap">
          {props.talkInfo.title}
        </div>
        <div className="">{props.talkInfo.writer}</div>
      </div>
      <Transition appear show={onModal} as={Fragment}>
        <Dialog as="div" className="relative z-10" onClose={modalCloseHandler}>
          <Transition.Child
            as={Fragment}
            enter="ease-out duration-300"
            enterFrom="opacity-0"
            enterTo="opacity-100"
            leave="ease-in duration-200"
            leaveFrom="opacity-100"
            leaveTo="opacity-0"
          >
            <div className="fixed inset-0 bg-black bg-opacity-25" />
          </Transition.Child>

          <div className="fixed inset-0 overflow-y-auto">
            <div className="flex min-h-full items-center justify-center p-4 text-center">
              <Transition.Child
                as={Fragment}
                enter="ease-out duration-300"
                enterFrom="opacity-0 scale-60"
                enterTo="opacity-100 scale-100"
                leave="ease-in duration-200"
                leaveFrom="opacity-100 scale-100"
                leaveTo="opacity-0 scale-95"
              >
                <Dialog.Panel className="w-full max-w-md transform overflow-hidden rounded-2xl bg-white p-4 text-left align-middle shadow-xl transition-all">
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
                      <p className="text-sm mt-2">
                        {talkDetail.createDate}
                      </p>
                    </div>
                    <div className="col-span-1 text-right">
                      <p>
                        {ppl}
                      </p>
                    </div>
                  </div>
                  {!enterTalk && (
                    <div>
                      <p>
                        {talkDetail.description}
                      </p>
                      <div className="mx-4 my-2 text-center">
                        <button onClick={enterHandler} className="px-4 py-2 transition-all bg-orange-400 rounded-lg hover:bg-orange-500">
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
