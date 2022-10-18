import { memo } from 'react';

import { CreateStateReturnValues } from 'hooks/useCreateState';
import { convertRemainTime, getNewDateString } from 'utils/date';

import {
  Container,
  SectionContainer,
  Heading,
  Input,
  LabelContainer,
  Label,
} from '../@shared/styled';
import * as S from './index.styled';

interface Step2Props {
  useDateState: CreateStateReturnValues['useDateState'];
  useDeadlineState: CreateStateReturnValues['useDeadlineState'];
  pressEnterToNext: (e: React.KeyboardEvent<HTMLInputElement>) => void;
}

function Step2({
  useDateState,
  useDeadlineState,
  pressEnterToNext,
}: Step2Props) {
  const { startDate, setStartDate, endDate, setEndDate } = useDateState();
  const { deadline, setDeadline } = useDeadlineState();

  const remainTime = convertRemainTime(deadline);

  return (
    <Container>
      <SectionContainer>
        <Heading>
          <span>언제부터 언제까지</span> 만날건가요?
        </Heading>
        <LabelContainer>
          <p>기간</p>
          <S.InputWrapper>
            <Input
              type="date"
              value={startDate}
              onChange={setStartDate}
              min={getNewDateString('day')}
            />
            ~
            <Input
              type="date"
              value={endDate}
              onChange={setEndDate}
              min={startDate || getNewDateString('day')}
              onKeyPress={pressEnterToNext}
            />
          </S.InputWrapper>
        </LabelContainer>
      </SectionContainer>
      <SectionContainer>
        <Heading>
          <span>언제까지</span> 모집할까요?
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
      </SectionContainer>
    </Container>
  );
}

export default memo(Step2);
