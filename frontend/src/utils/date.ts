import { GroupDetailData } from 'types/data';

const oneSecond = 1000;
const oneMinute = oneSecond * 60;
const oneHour = oneMinute * 60;
const oneDay = oneHour * 24;

const convertRemainTime = (deadline: string) => {
  const deadlineDate = new Date(deadline);
  const now = new Date();

  const gap = Number(deadlineDate) - Number(now);

  if (gap <= 0 || Number.isNaN(gap)) return null;

  const dayGap = Math.floor(gap / oneDay);
  const hourGap = Math.floor((gap / oneHour) % 24);
  const minGap = Math.floor((gap / oneMinute) % 60);
  const secGap = Math.floor((gap / oneSecond) % 60);

  if (dayGap > 0) return `${dayGap}일`;
  if (hourGap > 0) return `${hourGap}시간${minGap !== 0 ? ` ${minGap}분` : ''}`;
  if (minGap > 0) return `${minGap}분`;

  return `${secGap}초`;
};

const convertDeadlineToRemainTime = (deadline: GroupDetailData['deadline']) => {
  const remainTime = convertRemainTime(deadline);

  if (!remainTime) return '마감 완료';

  return `마감까지 ${remainTime}`;
};

const getTimeInKorea = (time: Date = new Date()) => {
  const offset = time.getTimezoneOffset() * 60 * 1000;
  return new Date(time.getTime() - offset);
};

const getNewDateString = (until: 'day' | 'min') => {
  const timeInKorea = getTimeInKorea();

  switch (until) {
    case 'day':
      return timeInKorea.toISOString().slice(0, 10);
    case 'min':
      return timeInKorea.toISOString().slice(0, 16);
  }
};

const resetDateToStartOfDay = (date: Date) => {
  const resetDate = new Date(date);
  resetDate.setHours(0, 0, 0, 0);
  return resetDate;
};

const resetDateToEndOfDay = (date: Date) => {
  const resetDate = new Date(date);
  resetDate.setHours(23, 59, 59, 999);
  return resetDate;
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

const parsedTime = (time: string) => {
  const [hour, minute] = time.split(':');

  return Number(minute) === 0 ? `${hour}시` : `${hour}시 ${minute}분`;
};

export {
  convertRemainTime,
  convertDeadlineToRemainTime,
  getNewDateString,
  getTimeInKorea,
  resetDateToStartOfDay,
  resetDateToEndOfDay,
  parsedDurationDate,
  parsedTime,
};
