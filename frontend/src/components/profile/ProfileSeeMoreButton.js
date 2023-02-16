import React from "react";

function ProfileSeeMoreButton(props) {
  return (
    <div className="text-center mt-4">
      <button className="text-white text-xl" onClick={props.seeMore}>
        더보기
      </button>
    </div>
  );
}

export default ProfileSeeMoreButton;
