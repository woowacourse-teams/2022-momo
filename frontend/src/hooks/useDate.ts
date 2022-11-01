import { useState } from 'react';

interface UseDateReturnType {
  year: number;
  month: number;
  goToPrevMonth: () => void;
  goToNextMonth: () => void;
}

const useDate = (): UseDateReturnType => {
  const today = new Date();
  const [year, setYear] = useState(today.getFullYear());
  const [month, setMonth] = useState(today.getMonth() + 1);

  const goToPrevMonth = (): void => {
    if (month <= 1) {
      setYear(prevState => prevState - 1);
      setMonth(12);
      return;
    }

    setMonth(prevState => prevState - 1);
  };

  const goToNextMonth = (): void => {
    if (month >= 12) {
      setYear(prevState => prevState + 1);
      setMonth(1);
      return;
    }

    setMonth(prevState => prevState + 1);
  };

  return { year, month, goToPrevMonth, goToNextMonth };
};

export default useDate;
