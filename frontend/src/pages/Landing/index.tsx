import { useEffect, useState } from 'react';

import useThrottle from 'hooks/useThrottle';

import { Step1, Step2, Step3, Step4, Step5 } from './Steps';

function Landing() {
  const [showedPageNumbers, setShowedPageNumbers] = useState<number[]>([1]);

  const throttledScrollEvent = useThrottle(() => {
    const scrollHeight = window.scrollY;

    if (scrollHeight < 300) {
      setShowedPageNumbers([1]);
      return;
    }

    if (scrollHeight < 1000) {
      setShowedPageNumbers([1, 2]);
      return;
    }

    if (scrollHeight < 1800) {
      setShowedPageNumbers([1, 2, 3]);
      return;
    }

    if (scrollHeight < 2500) {
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
