import { memo } from 'react';

import CalendarEditor from 'components/CalendarEditor';
import { CreateStateReturnValues } from 'hooks/useCreateState';
import { GroupDetailData } from 'types/data';

import { Container, Heading, SectionContainer } from '../@shared/styled';
interface Step3Props {
  useScheduleState: CreateStateReturnValues['useScheduleState'];
  duration: GroupDetailData['duration'];
  pressEnterToNext: (e: React.KeyboardEvent<HTMLInputElement>) => void;
}

function Step3({ useScheduleState, duration, pressEnterToNext }: Step3Props) {
  return (
    <Container>
      <SectionContainer>
        <Heading>
          <span>언제</span> 만날건가요?
        </Heading>
        <CalendarEditor
          useScheduleState={useScheduleState}
          duration={duration}
          pressEnterToNext={pressEnterToNext}
        />
      </SectionContainer>
    </Container>
  );
}

export default memo(Step3);
