import React from "react";

function ProfileSeeMoreButton(props) {
  return (
    <div className="mt-4 text-center">
      <button className="text-xl text-white" onClick={props.seeMore}>
        더보기
      </button>
    </div>
  );
}

export default ProfileSeeMoreButton;
