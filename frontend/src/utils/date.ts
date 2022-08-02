import { GroupDetailData } from 'types/data';

const convertRemainTime = (deadline: string) => {
  const deadlineDate = new Date(deadline);
  const now = new Date();

  const gap = Number(deadlineDate) - Number(now);

  if (gap <= 0 || Number.isNaN(gap)) return null;

  // TODO: 산술식 상수화 시키기
  const dayGap = Math.floor(gap / (1000 * 60 * 60 * 24));
  const hourGap = Math.floor((gap / (1000 * 60 * 60)) % 24);
  const minGap = Math.floor((gap / (1000 * 60)) % 60);
  const secGap = Math.floor((gap / 1000) % 60);

  if (dayGap > 0) return `${dayGap}일`;
  if (hourGap > 0) return `${hourGap}시간 ${minGap !== 0 ? `${minGap}분` : ''}`;
  if (minGap > 0) return `${minGap}분`;

  return `${secGap}초`;
};

const convertDeadlineToRemainTime = (deadline: GroupDetailData['deadline']) => {
  const remainTime = convertRemainTime(deadline);

  if (!remainTime) return '마감 완료';

  return `마감까지 ${remainTime}`;
};

const getNewDateString = (until: 'day' | 'min') => {
  switch (until) {
    case 'day':
      return new Date().toISOString().slice(0, 10);
    case 'min':
      return new Date().toISOString().slice(0, 16);
  }
};

const resetDateToMidnight = (date: Date) => {
  date.setHours(0, 0, 0, 0);

  return date;
};

const parsedDurationDate = (duration: GroupDetailData['duration']) => {
  const [startYear, startMonth, startDay] = duration.start.split('-');
  const parsedStartDate = `${startYear}년 ${startMonth}월 ${startDay}일`;
  const [endYear, endMonth, endDay] = duration.end.split('-');

  if (duration.start === duration.end) {
    return parsedStartDate;
  }

  if (startYear !== endYear) {
    const parsedEndDate = `${endYear}년 ${endMonth}월 ${endDay}일`;

    return `${parsedStartDate} ~ ${parsedEndDate}`;
  }

  if (startMonth !== endMonth) {
    const parsedEndDate = `${endMonth}월 ${endDay}일`;

    return `${parsedStartDate} ~ ${parsedEndDate}`;
  }

  return `${parsedStartDate} ~ ${endDay}일`;
};

export {
  convertRemainTime,
  convertDeadlineToRemainTime,
  getNewDateString,
  resetDateToMidnight,
  parsedDurationDate,
};
