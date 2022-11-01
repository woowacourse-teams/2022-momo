import { useState } from 'react';

interface UseClosingStateReturnType {
  isClosing: boolean;
  close: () => void;
}

const useClosingState = (
  animationTime: number,
  setOffFunc: () => void,
): UseClosingStateReturnType => {
  const [isClosing, setIsClosing] = useState(false);

  const close = (): void => {
    setIsClosing(true);

    setTimeout(() => {
      setOffFunc();
      setIsClosing(false);
    }, animationTime);
  };

  return { isClosing, close };
};

export default useClosingState;
