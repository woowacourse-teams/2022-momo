const isToday = (year: number, month: number, date: number) => {
  const today = new Date();

  return (
    today.getFullYear() === year &&
    today.getMonth() === month - 1 &&
    today.getDate() === date
  );
};

export { isToday };
