import { useState } from 'react';

const useClosingState = (animationTime: number, setOffFunc: Function) => {
  const [isClosing, setIsClosing] = useState(false);

  const close = () => {
    setIsClosing(true);

    setTimeout(() => {
      setOffFunc();
      setIsClosing(false);
    }, animationTime);
  };

  return { isClosing, close };
};

export default useClosingState;
