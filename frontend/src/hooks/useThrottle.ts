let timer = false;

const useThrottle = () => {
  const throttle = (callbackFunc: () => void, delay: number) => {
    if (timer) return;

    timer = true;

    callbackFunc();

    setTimeout(() => {
      timer = false;
    }, delay);
  };

  return { throttle };
};

export default useThrottle;
