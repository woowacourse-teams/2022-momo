import { RefObject, useEffect, useMemo, useRef, useState } from 'react';

import { Divider } from './@shared/index.styled';
import { Step1, Step2, Step3, Step4, Step5 } from './Steps';

function Landing() {
  const [showedPageNumbers, setShowedPageNumbers] = useState<number[]>([1]);

  const target1 = useRef<HTMLDivElement>(null);
  const target2 = useRef<HTMLDivElement>(null);
  const target3 = useRef<HTMLDivElement>(null);
  const target4 = useRef<HTMLDivElement>(null);
  const target5 = useRef<HTMLDivElement>(null);

  const targetArray: Array<RefObject<HTMLDivElement>> = useMemo(
    () => [target1, target2, target3, target4, target5],
    [],
  );

  useEffect(() => {
    const observers: IntersectionObserver[] = [];

    const onIntersection = (targetIndex: number) => () => {
      const currentDisplayedSteps = [...Array(targetIndex + 1)].map(
        (_, i) => i + 1,
      );

      setShowedPageNumbers(currentDisplayedSteps);
    };

    if (targetArray.length > 0) {
      targetArray.forEach((item, i) => {
        observers[i] = new IntersectionObserver(onIntersection(i), {
          threshold: 0.5,
        });

        if (!item.current) {
          return;
        }

        observers[i].observe(item.current);
      });
    }

    return () => {
      observers && observers.forEach(item => item.disconnect);
    };
  }, [targetArray]);

  return (
    <>
      <Divider ref={targetArray[0]} />
      <Step1 show={showedPageNumbers.includes(1)} />
      <Divider ref={targetArray[1]} />
      <Step2 show={showedPageNumbers.includes(2)} />
      <Divider ref={targetArray[2]} />
      <Step3 show={showedPageNumbers.includes(3)} />
      <Divider ref={targetArray[3]} />
      <Step4 show={showedPageNumbers.includes(4)} />
      <Divider ref={targetArray[4]} />
      <Step5 show={showedPageNumbers.includes(5)} />
    </>
  );
}

export default Landing;
