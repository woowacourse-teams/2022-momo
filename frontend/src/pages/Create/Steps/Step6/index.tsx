import { forwardRef, LegacyRef, memo } from 'react';

import { CreateStateReturnValues } from 'hooks/useCreateState';
import { convertRemainTime, getNewDateString } from 'utils/date';

import {
  Container,
  ErrorColor,
  Heading,
  Input,
  LabelContainer,
  Label,
} from '../@shared/styled';

interface Step6Props {
  useDeadlineState: CreateStateReturnValues['useDeadlineState'];
  pressEnterToNext: (e: React.KeyboardEvent<HTMLInputElement>) => void;
}

function Step6(
  { useDeadlineState, pressEnterToNext }: Step6Props,
  ref: LegacyRef<HTMLDivElement>,
) {
  const { deadline, setDeadline } = useDeadlineState();

  const remainTime = convertRemainTime(deadline);

  return (
    <Container ref={ref}>
      <Heading>
        <span>언제까지</span> 모집할까요? <ErrorColor>*</ErrorColor>
      </Heading>
      <LabelContainer>
        <Label>
          <p>날짜</p>
          <p>{remainTime && `${remainTime} 후`}</p>
        </Label>
        <Input
          type="datetime-local"
          value={deadline}
          onChange={setDeadline}
          onKeyPress={pressEnterToNext}
          min={getNewDateString('min')}
        />
      </LabelContainer>
    </Container>
  );
}

export default memo(forwardRef(Step6));
