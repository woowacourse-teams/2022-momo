import {
  convertRemainTime,
  convertDeadlineToRemainTime,
  getNewDateString,
  resetDateToStartOfDay,
  resetDateToEndOfDay,
  parsedDurationDate,
  parsedTime,
  getTimeInKorea,
  isToday,
} from './date';

const fakeTimerSetUp = () => {
  jest.useFakeTimers();
  jest.setSystemTime(new Date('2022-01-01'));
};

describe('convertRemainTime 함수에 목표 시간을 넣어 오늘로부터 남은 시간을 계산할 수 있다.', () => {
  beforeAll(() => {
    fakeTimerSetUp();
  });

  it('지난 시간을 넣으면 null을 반환한다.', () => {
    const pastTime = '2021-12-31T00:00:01.000Z';
    expect(convertRemainTime(pastTime)).toBeNull();
  });

  it('미래의 시간을 넣으면 남은 시간 값을 초 단위로 반환한다.', () => {
    const afterOneSecond = '2022-01-01T00:00:01.000Z';

    expect(convertRemainTime(afterOneSecond)).toBe('1초');
  });

  it('미래의 시간을 넣으면 남은 시간 값을 분 단위로 반환한다.', () => {
    const afterOneMinute = '2022-01-01T00:01:30.000Z';

    expect(convertRemainTime(afterOneMinute)).toBe('1분');
  });

  it('미래의 시간을 넣으면 남은 시간 값을 시간 단위로 반환한다.', () => {
    const afterOneHalfHour = '2022-01-01T01:30:00.000Z';

    expect(convertRemainTime(afterOneHalfHour)).toBe('1시간');
  });

  it('미래의 시간을 넣으면 남은 시간 값을 일 단위로 반환한다.', () => {
    const afterOneDay = '2022-01-02T01:00:00.000Z';

    expect(convertRemainTime(afterOneDay)).toBe('1일');
  });
});

describe('convertDeadlineToRemainTime 함수에 목표 시간을 넣으면 오늘로부터 남은 시간을 문자열로 가공하여 볼 수 있다.', () => {
  beforeAll(() => {
    fakeTimerSetUp();
  });

  it('지난 시간을 넣으면 마감 완료를 반환한다.', () => {
    const pastDay = '2021-12-31T00:00:00.000Z';

    expect(convertDeadlineToRemainTime(pastDay)).toBe('마감 완료');
  });

  it('미래의 시간을 넣으면 남은 시간 값을 가공된 초 단위로 반환한다.', () => {
    const afterOneSecond = '2022-01-01T00:00:01.000Z';

    expect(convertDeadlineToRemainTime(afterOneSecond)).toBe('마감까지 1초');
  });

  it('미래의 시간을 넣으면 남은 시간 값을 가공된 분 단위로 반환한다.', () => {
    const afterOneHalfMinute = '2022-01-01T00:01:30.000Z';

    expect(convertDeadlineToRemainTime(afterOneHalfMinute)).toBe(
      '마감까지 1분',
    );
  });

  it('미래의 시간을 넣으면 남은 시간 값을 가공된 시간 단위로 반환한다.', () => {
    const afterOneHalfHour = '2022-01-01T01:30:00.000Z';

    expect(convertDeadlineToRemainTime(afterOneHalfHour)).toBe(
      '마감까지 1시간',
    );
  });

  it('미래의 시간을 넣으면 남은 시간 값을 가공된 일 단위로 반환한다.', () => {
    const afterOneDay = '2022-01-02T01:00:00.000Z';

    expect(convertDeadlineToRemainTime(afterOneDay)).toBe('마감까지 1일');
  });
});

describe('getNewDateString 함수를 사용하면 현재 시간을 ISO 형식으로 가공하여 볼 수 있다.', () => {
  beforeAll(() => {
    fakeTimerSetUp();
  });

  it('인자로 day를 넣으면 일 단위까지의 ISO 형식 문자열을 반환한다.', () => {
    expect(getNewDateString('day')).toBe('2022-01-01');
  });

  it('인자로 min를 넣으면 분 단위까지의 ISO 형식 문자열을 반환한다.', () => {
    expect(getNewDateString('min')).toBe('2022-01-01T09:00');
  });
});

describe('resetDateTo___OfDay 함수를 사용하면 하루의 시작과 끝으로 초기화 된 Date 객체를 얻을 수 있다.', () => {
  beforeAll(() => {
    fakeTimerSetUp();
  });

  it('resetDateToStartOfDay에 Date 객체를 넣으면 오늘 자정으로 초기화 된 Date 객체를 반환한다.', () => {
    const noonDate = new Date('2022-01-01T12:00:00.000Z');
    const midNightDate = noonDate;
    midNightDate.setHours(0, 0, 0, 0);

    expect(getTimeInKorea(resetDateToStartOfDay(noonDate))).toStrictEqual(
      getTimeInKorea(new Date(midNightDate)),
    );
  });

  it('resetDateToEndOfDay에 Date 객체를 넣으면 내일 자정 직전으로 초기화 된 Date 객체를 반환한다.', () => {
    const noonDate = new Date('2022-01-01T12:00:00.000Z');
    const endDate = noonDate;
    endDate.setHours(23, 59, 59, 999);

    expect(getTimeInKorea(resetDateToEndOfDay(noonDate))).toStrictEqual(
      getTimeInKorea(new Date(endDate)),
    );
  });
});

describe('parsedDurationDate 함수를 사용하면 가공된 Date 문자열을 반환한다.', () => {
  beforeAll(() => {
    fakeTimerSetUp();
  });

  it('시작 날짜와 종료 날짜가 같으면 하나의 날짜만 반환한다.', () => {
    const oneDayDuration = { start: '2022-01-01', end: '2022-01-01' };

    expect(parsedDurationDate(oneDayDuration)).toBe('2022년 01월 01일');
  });

  it('시작 날짜와 종료 날짜가 일자만 다르면 종료 날짜는 일자만 반환한다.', () => {
    const oneDayGapDuration = { start: '2022-01-01', end: '2022-01-02' };

    expect(parsedDurationDate(oneDayGapDuration)).toBe(
      '2022년 01월 01일 ~ 02일',
    );
  });

  it('시작 날짜와 종료 날짜가 달만 다르면 종료 날짜는 달부터 일까지만 반환한다.', () => {
    const oneMonthGapDuration = { start: '2022-01-01', end: '2022-02-01' };

    expect(parsedDurationDate(oneMonthGapDuration)).toBe(
      '2022년 01월 01일 ~ 02월 01일',
    );
  });

  it('시작 날짜와 종료 날짜가 연도까지 다르면 종료 날짜는 모두 반환한다.', () => {
    const oneYearGapDuration = { start: '2022-01-01', end: '2023-01-01' };

    expect(parsedDurationDate(oneYearGapDuration)).toBe(
      '2022년 01월 01일 ~ 2023년 01월 01일',
    );
  });
});

describe('parsedTime 함수를 사용하면 시간을 가공하여 반환한다.', () => {
  beforeAll(() => {
    fakeTimerSetUp();
  });

  it('분 단위가 0이면 시간까지만 반환한다.', () => {
    const rowTimeString = '18:00:00';

    expect(parsedTime(rowTimeString)).toBe('18시');
  });

  it('분 단위가 0이 아니면 분 단위까지 반환한다.', () => {
    const rowTimeString = '18:30:00';

    expect(parsedTime(rowTimeString)).toBe('18시 30분');
  });
});

describe('isToday 함수를 사용하면 전달한 날짜가 오늘인지의 여부를 반환한다.', () => {
  it('연도, 월, 일을 전달하면 오늘이 맞는지를 확인하여 반환한다.', () => {
    fakeTimerSetUp();

    expect(isToday(2022, 1, 1)).toBeTruthy();
  });
});
