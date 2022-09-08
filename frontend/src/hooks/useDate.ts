import { useState } from 'react';

const useDate = () => {
  const today = new Date();
  const [year, setYear] = useState(today.getFullYear());
  const [month, setMonth] = useState(today.getMonth() + 1);

  const goToPrevMonth = () => {
    if (month <= 1) {
      setYear(prevState => prevState - 1);
      setMonth(12);
      return;
    }

    setMonth(prevState => prevState - 1);
  };

  const goToNextMonth = () => {
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
