import { GROUP_RULE } from 'constants/rule';
import { CreateGroupData } from 'types/data';
import { copyObject } from 'utils/object';

import { validateDeadlineWithNow, validateGroupData } from './validate';

const exampleCreateGroupData: CreateGroupData = {
  name: '잠실 루터회관 모임',
  selectedCategory: { id: 1, name: '스터디' },
  capacity: 2,
  startDate: '2080-10-10',
  endDate: '2080-11-10',
  schedules: [{ date: '2080-10-11', startTime: '06:00', endTime: '09:00' }],
  deadline: '2080-09-30',
  location: {
    address: '서울 송파구 올림픽로35다길 42(한국루터회관)',
    buildingName: '한국루터회관',
    detail: '14층',
  },
  description: '잠실 루터회관에서 모임을 주최합니다! 모두 참석해주세요!',
};

describe('필수 값과 선택 값들을 넣어 모임을 생성하도록 요청할 수 있다.', () => {
  it('모든 필드를 입력한 경우 모임 생성 요청이 가능해야한다.', () => {
    // given
    const fulfilledCreateGroupData = copyObject(exampleCreateGroupData);

    // when & then
    expect(() => validateGroupData(fulfilledCreateGroupData)).not.toThrow();
  });

  it('모임 이름을 입력하지 않은 경우 모임 생성 요청이 가능하지 않아야 한다.', () => {
    // given
    const fulfilledCreateGroupData = copyObject(exampleCreateGroupData);
    delete fulfilledCreateGroupData.name;
    const noNameCreateGroupData = fulfilledCreateGroupData;

    // when & then
    expect(() => validateGroupData(noNameCreateGroupData)).toThrow();
  });

  it('유효하지 않은 모임 이름을 입력한 경우 모임 생성 요청이 가능하지 않아야 한다.', () => {
    // given
    const fulfilledCreateGroupData = copyObject(exampleCreateGroupData);
    fulfilledCreateGroupData.name = '';
    const noNameCreateGroupData = fulfilledCreateGroupData;

    // when & then
    expect(() => validateGroupData(noNameCreateGroupData)).toThrow();
  });

  it('유효하지 않은 모집 인원을 입력한 경우 모임 생성 요청이 가능하지 않아야 한다.', () => {
    // given
    const zeroCapacityCreateGroupData = copyObject(exampleCreateGroupData);
    const tooLowCapacityCreateGroupData = copyObject(exampleCreateGroupData);
    const tooHighCapacityCreateGroupData = copyObject(exampleCreateGroupData);

    // when
    zeroCapacityCreateGroupData.capacity = 0;
    tooLowCapacityCreateGroupData.capacity = -1;
    tooHighCapacityCreateGroupData.capacity = GROUP_RULE.CAPACITY.MAX + 1;

    // then
    expect(() => validateGroupData(zeroCapacityCreateGroupData)).not.toThrow();
    expect(() => validateGroupData(tooLowCapacityCreateGroupData)).toThrow();
    expect(() => validateGroupData(tooHighCapacityCreateGroupData)).toThrow();
  });

  it('진행 날짜를 선택하지 않은 경우 모임 생성 요청이 가능하지 않아야 한다.', () => {
    // given
    const fulfilledCreateGroupData = copyObject(exampleCreateGroupData);
    const noStartDateCreateGroupData = copyObject(fulfilledCreateGroupData);
    const noEndDateCreateGroupData = copyObject(fulfilledCreateGroupData);

    // when
    delete noStartDateCreateGroupData.startDate;
    delete noEndDateCreateGroupData.endDate;

    // then
    expect(() => validateGroupData(noStartDateCreateGroupData)).toThrow();
    expect(() => validateGroupData(noEndDateCreateGroupData)).toThrow();
  });

  it('일정을 선택하지 않은 경우에도 모임 생성 요청이 가능해야 한다.', () => {
    // given
    const noSchedulesCreateGroupData = copyObject(exampleCreateGroupData);

    // when
    noSchedulesCreateGroupData.schedules = [];

    // then
    expect(() => validateGroupData(noSchedulesCreateGroupData)).not.toThrow();
  });

  it('마감 일자를 선택하지 않은 경우 모임 생성 요청이 가능하지 않아야 한다.', () => {
    // given
    const noDeadlineCreateGroupData = copyObject(exampleCreateGroupData);

    // when
    delete noDeadlineCreateGroupData.deadline;

    // then
    expect(() => validateGroupData(noDeadlineCreateGroupData)).toThrow();
  });

  it('장소를 선택하지 않은 경우에도 모임 생성 요청이 가능해야 한다.', () => {
    // given
    const noLocationCreateGroupData = copyObject(exampleCreateGroupData);

    // when
    noLocationCreateGroupData.location.address = '';
    noLocationCreateGroupData.location.buildingName = '';
    noLocationCreateGroupData.location.detail = '';

    // then
    expect(() => validateGroupData(noLocationCreateGroupData)).not.toThrow();
  });

  it('상세 정보를 입력하지 않은 경우에도 모임 생성 요청이 가능해야 한다.', () => {
    // given
    const noDescriptionCreateGroupData = copyObject(exampleCreateGroupData);

    // when
    noDescriptionCreateGroupData.description = '';

    // then
    expect(() => validateGroupData(noDescriptionCreateGroupData)).not.toThrow();
  });
});

describe('주어진 모임 데이터를 검증할 수 있다.', () => {
  it('마감일이 현재 시간보다 이후인지 검증할 수 있다.', () => {
    // given & when
    const parsedValidDeadline = new Date('2080-09-30');
    const parsedInvalidDeadline = new Date('1980-09-30');

    // then
    expect(validateDeadlineWithNow(parsedValidDeadline)).toBeTruthy();
    expect(validateDeadlineWithNow(parsedInvalidDeadline)).toBeFalsy();
  });
});
