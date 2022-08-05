import { forwardRef, LegacyRef, memo, useState } from 'react';

import useInput from 'hooks/useInput';
import { CreateGroupData, ScheduleType } from 'types/data';

import { Container, Heading } from '../@shared/styled';
import Calendar from './Calendar';
import * as S from './index.styled';

interface Step5Props {
  useScheduleState: () => {
    schedules: CreateGroupData['schedules'];
    setSchedules: (schedule: ScheduleType) => void;
  };
  duration: {
    start: CreateGroupData['startDate'];
    end: CreateGroupData['endDate'];
  };
  pressEnterToNext: (e: React.KeyboardEvent<HTMLInputElement>) => void;
}

// TODO: duration이 바뀌면 schedules 날림
// TODO: schedule 관련 코드 리팩토링
function Step5(
  { useScheduleState, duration, pressEnterToNext }: Step5Props,
  ref: LegacyRef<HTMLDivElement>,
) {
  const { schedules, setSchedules } = useScheduleState();

  const [selectedDate, setSelectedDate] = useState('');
  const {
    value: startTime,
    setValue: setStartTime,
    dangerouslySetValue: dangerouslySetStartTime,
  } = useInput('');
  const {
    value: endTime,
    setValue: setEndTime,
    dangerouslySetValue: dangerouslySetEndTime,
  } = useInput('');

  const selectDate = (year: number, month: number, date: number) => {
    setSelectedDate(
      `${year}-${month.toString().padStart(2, '0')}-${date
        .toString()
        .padStart(2, '0')}`,
    );
  };

  const addSchedule = () => {
    if (startTime >= endTime) {
      alert('시작 시간은 종료 시간 이전이어야 해요.');
      return;
    }

    if (selectedDate < duration.start || selectedDate > duration.end) {
      alert('잘못된 날짜예요. 다시 선택해주세요 😤');
    }

    const schedule = {
      date: selectedDate,
      startTime,
      endTime,
    };

    setSchedules(schedule);
    setSelectedDate('');
    dangerouslySetStartTime('');
    dangerouslySetEndTime('');
  };

  return (
    <Container ref={ref}>
      <Heading>
        <span>언제</span> 만날건가요?
      </Heading>
      <S.Content>
        <S.Left>
          <Calendar
            duration={duration}
            schedules={schedules}
            selectDate={selectDate}
            selectedDate={selectedDate}
          />
        </S.Left>
        <S.Right>
          <S.InputWrapper>
            <S.Input
              type="time"
              value={startTime}
              onChange={setStartTime}
              disabled={!selectedDate}
            />
            부터
            <S.Input
              type="time"
              value={endTime}
              onChange={setEndTime}
              onKeyPress={pressEnterToNext}
              disabled={!selectedDate}
            />
            까지
          </S.InputWrapper>
          <S.AddButton type="button" onClick={addSchedule}>
            달력에 추가하기
          </S.AddButton>
        </S.Right>
      </S.Content>
    </Container>
  );
}

export default memo(forwardRef(Step5));
