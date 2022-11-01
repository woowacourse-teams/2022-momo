import { useState } from 'react';

interface UseHoverReturnType {
  isHover: boolean;
  changeHoverState: (isHover: boolean) => () => void;
}

const useHover = (): UseHoverReturnType => {
  const [isHover, setIsHover] = useState(false);

  const changeHoverState = (isHover: boolean) => (): void => {
    setIsHover(isHover);
  };

  return { isHover, changeHoverState };
};

export default useHover;
