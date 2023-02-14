import React from "react";
import { FaAngleUp } from "react-icons/fa";

function MovieScrollTopButton(props) {
  return (
    <div className="text-right">
      <button
        onClick={props.scrollToTop}
        className="fixed right-5 bottom-5 text-white bg-[#FA9E13] w-12 h-12 rounded-full font-bold text-xl bg-opacity-50 hover:bg-opacity-100"
      >
        <FaAngleUp className="m-auto" size="30" />
      </button>
    </div>
  );
}

export default MovieScrollTopButton;
