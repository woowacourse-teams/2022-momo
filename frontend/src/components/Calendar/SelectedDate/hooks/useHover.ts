import { useState } from 'react';

const useHover = () => {
  const [isHover, setIsHover] = useState(false);

  const changeHoverState = (isHover: boolean) => () => {
    setIsHover(isHover);
  };

  return { isHover, changeHoverState };
};

export default useHover;
