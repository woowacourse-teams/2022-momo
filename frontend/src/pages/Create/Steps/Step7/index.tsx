import { memo, forwardRef, LegacyRef } from 'react';

import { GROUP_RULE } from 'constants/rule';
import { CreateStateReturnValues } from 'hooks/useCreateState';

import { Container, Heading, Input, Label } from '../@shared/styled';
import * as S from './index.styled';

interface Step7Props {
  useLocationState: CreateStateReturnValues['useLocationState'];
  pressEnterToNext: (e: React.KeyboardEvent<HTMLInputElement>) => void;
}

function Step7(
  { useLocationState, pressEnterToNext }: Step7Props,
  ref: LegacyRef<HTMLDivElement>,
) {
  const { location, setLocation } = useLocationState();

  return (
    <Container ref={ref}>
      <Heading>
        <span>어디에서</span> 모일까요?
      </Heading>
      <S.LabelContainer>
        <Label>
          <p>장소</p>
          <p>
            {location.length}/{GROUP_RULE.LOCATION.MAX_LENGTH}
          </p>
        </Label>
        <Input
          type="text"
          value={location}
          onChange={setLocation}
          onKeyPress={pressEnterToNext}
          placeholder="이춘복참치 잠실점"
          maxLength={GROUP_RULE.LOCATION.MAX_LENGTH}
        />
      </S.LabelContainer>
    </Container>
  );
}

export default memo(forwardRef(Step7));
