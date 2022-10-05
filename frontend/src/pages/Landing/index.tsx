import { useEffect, useState } from 'react';

import useThrottle from 'hooks/useThrottle';

import { Step1, Step2, Step3, Step4, Step5 } from './Steps';

const scrollHeightByPage = {
  1: 300,
  2: 1000,
  3: 1800,
  4: 2500,
};

function Landing() {
  const [showedPageNumbers, setShowedPageNumbers] = useState<number[]>([1]);

  const throttledScrollEvent = useThrottle(() => {
    const scrollHeight = window.scrollY;

    if (scrollHeight < scrollHeightByPage[1]) {
      setShowedPageNumbers([1]);
      return;
    }

    if (scrollHeight < scrollHeightByPage[2]) {
      setShowedPageNumbers([1, 2]);
      return;
    }

    if (scrollHeight < scrollHeightByPage[3]) {
      setShowedPageNumbers([1, 2, 3]);
      return;
    }

    if (scrollHeight < scrollHeightByPage[4]) {
      setShowedPageNumbers([1, 2, 3, 4]);
      return;
    }

    setShowedPageNumbers([1, 2, 3, 4, 5]);
  }, 100);

  useEffect(() => {
    window.addEventListener('scroll', throttledScrollEvent);

    return () => window.removeEventListener('scroll', throttledScrollEvent);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      <Step1 show={showedPageNumbers.includes(1)} />
      <Step2 show={showedPageNumbers.includes(2)} />
      <Step3 show={showedPageNumbers.includes(3)} />
      <Step4 show={showedPageNumbers.includes(4)} />
      <Step5 show={showedPageNumbers.includes(5)} />
    </>
  );
}

export default Landing;
