const useCalendar = (year: number, month: number) => {
  // 이번 달
  const totalOfDay = new Date(year, month, 0).getDate();
  const dates = [...Array(totalOfDay)].map((_, idx) => idx + 1);

  // 지난 달
  const prevMonth = new Date(year, month - 1, 0);
  const prevTotalOfDay = prevMonth.getDate();
  const prevTotalOfDayToShow = (prevMonth.getDay() + 1) % 7;
  const prevDates = [...Array(prevTotalOfDayToShow)]
    .map((_, idx) => prevTotalOfDay - idx)
    .reverse();

  // 다음 달
  const nextTotalOfDayToShow =
    new Date(year, month - 1, totalOfDay).getDay() + 1;
  const nextDates = [...Array(7 - nextTotalOfDayToShow)].map(
    (_, idx) => idx + 1,
  );

  return {
    dates,
    prevDates,
    nextDates,
  };
};

export default useCalendar;
