import { useState } from 'react';

const useClosingState = (animationTime: number, setOffFunc: () => void) => {
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
