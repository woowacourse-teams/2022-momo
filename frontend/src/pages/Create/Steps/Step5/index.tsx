import { forwardRef, LegacyRef, memo } from 'react';

import { CreateGroupData, ScheduleType } from 'types/data';

import { Container, Heading } from '../@shared/styled';
import CalendarEditor from './CalendarEditor';

interface Step5Props {
  useScheduleState: () => {
    schedules: CreateGroupData['schedules'];
    setSchedules: (schedule: ScheduleType) => void;
    deleteSchedule: (targetSchedule: ScheduleType) => void;
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
  return (
    <Container ref={ref}>
      <Heading>
        <span>언제</span> 만날건가요?
      </Heading>
      <CalendarEditor
        useScheduleState={useScheduleState}
        duration={duration}
        pressEnterToNext={pressEnterToNext}
      />
    </Container>
  );
}

export default memo(forwardRef(Step5));
