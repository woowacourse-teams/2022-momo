const useThrottle = (func: Function, wait: number): (() => void) => {
  let waiting = false;

  function throttledFunc() {
    if (waiting) return;

    // eslint-disable-next-line prefer-rest-params
    func.apply(typeof func, arguments);
    waiting = true;

    setTimeout(() => {
      waiting = false;
    }, wait);
  }

  return throttledFunc;
};

export default useThrottle;
