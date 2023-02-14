import Profile from "../../../assets/img/profile.png";

function ProfileCard(props) {
  
  return (
    <div className="grid h-full grid-rows-6 p-5">
      <div className="row-span-2 text-center border-b">
        {props.imagePath && <img
          className="w-32 h-32 m-auto rounded-full border-1"
          src={props.imagePath}
          alt=""
        />}
        {!props.imagePath && <img
          className="w-32 h-32 m-auto rounded-full border-1"
          src={Profile}
          alt=""
        />}
        
        <span className="mr-3 text-xl font-bold">{props.nicknameValue}</span>
        <button onClick={props.openEditor} className="text-sm">수정</button>
      </div>
      <div className="row-span-3 p-2 border-b">
       <span>#{props.gerne1} #{props.gerne2} #{props.gerne3}</span>
      </div>
      <div className="flex items-center justify-center row-span-1">
        <div className="text-xl font-bold ">달:뜸</div>
      </div>
    </div>
  );
}

export default ProfileCard;
