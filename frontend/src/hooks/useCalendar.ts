const useCalendar = (year: number, month: number) => {
  // 이번 달
  const lastDate = new Date(year, month, 0).getDate();
  const dates = [...Array(lastDate)].map((_, idx) => idx + 1);

  // 지난 달
  const prevMonth = new Date(year, month - 1, 0);
  const prevLastDate = prevMonth.getDate();
  const prevDateLengthToShow = (prevMonth.getDay() + 1) % 7;
  const prevDates = [...Array(prevDateLengthToShow)]
    .map((_, idx) => prevLastDate - idx)
    .reverse();

  // 다음 달
  const nextDateLengthToShow = new Date(year, month - 1, lastDate).getDay() + 1;
  const nextDates = [...Array(7 - nextDateLengthToShow)].map(
    (_, idx) => idx + 1,
  );

  return {
    dates,
    prevDates,
    nextDates,
  };
};

export default useCalendar;
