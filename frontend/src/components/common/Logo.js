import React from "react";
import { useNavigate } from "react-router";

import LogoImg from "../../assets/img/Logo.png";

function Logo(props) {
    const movePage = useNavigate();

    function goMain() {
        movePage("/");
      }


  return (
    <div className="flex col-span-1 cursor-pointer" onClick={goMain}>
      <img className="mr-4 w-9 h-9" src={LogoImg} alt="로고 이미지" />
      {props.color && <p className="text-3xl text-[#315B4C]">달:뜸</p>}
      {!props.color && <p className="text-3xl text-white">달:뜸</p>}
    </div>
  );
}

export default Logo;
