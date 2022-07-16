import { forwardRef, LegacyRef, memo } from 'react';

import { convertRemainTime, getNewDateString } from 'utils/date';

import {
  Container,
  ErrorColor,
  Heading,
  Input,
  LabelContainer,
  Label,
} from '../@shared/styled';

interface Step5Props {
  useDeadlineState: () => {
    deadline: string;
    setDeadline: (deadline: string) => void;
  };
  pressEnterToNext: (e: React.KeyboardEvent<HTMLInputElement>) => void;
}

function Step5(
  { useDeadlineState, pressEnterToNext }: Step5Props,
  ref: LegacyRef<HTMLDivElement>,
) {
  const { deadline, setDeadline } = useDeadlineState();
  const changeDeadline = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDeadline(e.target.value);
  };

  return (
    <Container ref={ref}>
      <Heading>
        <span>언제까지</span> 모집할까요? <ErrorColor>*</ErrorColor>
      </Heading>
      <LabelContainer>
        <Label>
          <p>날짜</p>
          <p>{deadline && `${convertRemainTime(deadline)} 후`}</p>
        </Label>
        <Input
          type="datetime-local"
          value={deadline}
          onChange={changeDeadline}
          onKeyPress={pressEnterToNext}
          min={getNewDateString('min')}
        />
      </LabelContainer>
    </Container>
  );
}

export default memo(forwardRef(Step5));
