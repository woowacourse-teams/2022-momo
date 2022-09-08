/**
 * 년, 월, 일 데이터를 ISO 8601 형식 문자열로 변환하는 함수입니다.
 * return 'YYYY-MM-DD'
 */
const convertToISOString = (year: number, month: number, date: number) => {
  return `${year}-${month.toString().padStart(2, '0')}-${date
    .toString()
    .padStart(2, '0')}`;
};

const isToday = (year: number, month: number, date: number) => {
  const today = new Date();

  return (
    today.getFullYear() === year &&
    today.getMonth() === month - 1 &&
    today.getDate() === date
  );
};

export { convertToISOString, isToday };
