import { forwardRef, LegacyRef, memo } from 'react';

import CalendarEditor from 'components/CalendarEditor';
import { CreateStateReturnValues } from 'hooks/useCreateState';
import { GroupDetailData } from 'types/data';

import { Container, Heading } from '../@shared/styled';

interface Step5Props {
  useScheduleState: CreateStateReturnValues['useScheduleState'];
  duration: GroupDetailData['duration'];
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
