const useCalendar = (year: number, month: number) => {
  // 이번 달
  const dayLength = new Date(year, month, 0).getDate();
  const dates = [...Array(dayLength)].map((_, idx) => idx + 1);

  // 지난 달
  const prevMonth = new Date(year, month - 1, 0);
  const prevDayLength = prevMonth.getDate();
  const prevDayLengthToShow = (prevMonth.getDay() + 1) % 7;
  const prevDates = [...Array(prevDayLengthToShow)]
    .map((_, idx) => prevDayLength - idx)
    .reverse();

  // 다음 달
  const nextDayLengthToShow = new Date(year, month - 1, dayLength).getDay() + 1;
  const nextDates = [...Array(7 - nextDayLengthToShow)].map(
    (_, idx) => idx + 1,
  );

  return {
    dates,
    prevDates,
    nextDates,
  };
};

export default useCalendar;
