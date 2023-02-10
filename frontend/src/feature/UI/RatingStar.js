import React, { useState, useEffect } from "react";
import { FaMoon } from "react-icons/fa";
import styled from 'styled-components';

const ARRAY = [0, 1, 2, 3, 4];

function RatingStar(props) {
  const [clicked, setClicked] = useState([false, false, false, false, false]);

  const handleStarClick = (index) => {
    let clickStates = [...clicked];
    for (let i = 0; i < 5; i++) {
      clickStates[i] = i <= index ? true : false;
    }
    setClicked(clickStates);
  };

  useEffect(() => {
    let score = clicked.filter(Boolean).length;
    props.sendScore(score);
  }, [clicked, props]);

  return (
    <>
      <Stars>
        {ARRAY.map((el, idx) => {
          return (
            <FaMoon
              key={idx}
              size="30"
              onClick={() => handleStarClick(el)}
              className={clicked[el] && 'yellowStar'}
            />
          );
        })}
      </Stars>
    </>
  );
}

export default RatingStar;

const Stars = styled.div`
  display: flex;

  & svg {
    color: gray;
    cursor: pointer;
  }

  :hover svg {
    color: #fcc419;
  }

  & svg:hover ~ svg {
    color: gray;
  }

  .yellowStar {
    color: #fcc419;
  }
`;