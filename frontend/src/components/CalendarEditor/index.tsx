import { useState } from 'react';

import { CalendarSVG, ClockSVG } from 'assets/svg';
import { CLIENT_ERROR_MESSAGE } from 'constants/message';
import useInput from 'hooks/useInput';
import { CreateGroupData, ScheduleType } from 'types/data';
import { convertToISOString } from 'utils/date';

import Calendar from './Calendar';
import * as S from './index.styled';

const svgSize = 20;

interface CalendarEditorProps {
  useScheduleState: () => {
    schedules: CreateGroupData['schedules'];
    setSchedules: (schedule: ScheduleType) => void;
    deleteSchedule: (targetSchedule: ScheduleType) => void;
  };
  duration: {
    start: CreateGroupData['startDate'];
    end: CreateGroupData['endDate'];
  };
  pressEnterToNext?: (e: React.KeyboardEvent<HTMLInputElement>) => void;
}

function CalendarEditor({
  useScheduleState,
  duration,
  pressEnterToNext = () => {},
}: CalendarEditorProps) {
  const { schedules, setSchedules, deleteSchedule } = useScheduleState();

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
    setSelectedDate(convertToISOString(year, month, date));
  };

  const addSchedule = () => {
    if (startTime >= endTime) {
      alert(CLIENT_ERROR_MESSAGE.CREATE.SCHEDULE_TIME);
      return;
    }

    if (selectedDate < duration.start || selectedDate > duration.end) {
      alert(CLIENT_ERROR_MESSAGE.CREATE.SCHEDULE_DAY);
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

  const deleteCurrentSchedule = () => {
    const schedule = {
      date: selectedDate,
      startTime,
      endTime,
    };

    deleteSchedule(schedule);
    setSelectedDate('');
    dangerouslySetStartTime('');
    dangerouslySetEndTime('');
  };

  const getSchedule = (date: string) => {
    return schedules.find(schedule => schedule.date === date);
  };

  return (
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
        <S.Container>
          <S.TimeContainer>
            <S.Wrapper>
              {selectedDate && (
                <>
                  <CalendarSVG width={svgSize} />
                  <S.Text>{selectedDate}</S.Text>
                </>
              )}
            </S.Wrapper>
            <S.Wrapper>
              {getSchedule(selectedDate) && (
                <>
                  <ClockSVG width={svgSize} />
                  <S.Text>
                    {`${getSchedule(selectedDate)?.startTime.slice(0, 5)} ~ 
                ${getSchedule(selectedDate)?.endTime.slice(0, 5)}`}
                  </S.Text>
                </>
              )}
            </S.Wrapper>
          </S.TimeContainer>
        </S.Container>
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
        {getSchedule(selectedDate) ? (
          <S.DeleteButton type="button" onClick={deleteCurrentSchedule}>
            일정 삭제하기
          </S.DeleteButton>
        ) : (
          <S.AddButton type="button" onClick={addSchedule}>
            달력에 추가하기
          </S.AddButton>
        )}
      </S.Right>
    </S.Content>
  );
}

export default CalendarEditor;
