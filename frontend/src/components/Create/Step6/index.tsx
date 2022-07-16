import { memo, forwardRef, LegacyRef } from 'react';

import { GROUP_RULE } from 'constants/rule';

import { Container, Heading, Input, Label } from '../@shared/styled';
import * as S from './index.styled';

interface Step6Props {
  useLocationState: () => {
    location: string;
    setLocation: (location: string) => void;
  };
  pressEnterToNext: (e: React.KeyboardEvent<HTMLInputElement>) => void;
}

function Step6(
  { useLocationState, pressEnterToNext }: Step6Props,
  ref: LegacyRef<HTMLDivElement>,
) {
  const { location, setLocation } = useLocationState();
  const changeLocation = (e: React.ChangeEvent<HTMLInputElement>) => {
    setLocation(e.target.value);
  };

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
          onChange={changeLocation}
          onKeyPress={pressEnterToNext}
          placeholder="둘둘치킨 선릉공원점"
          maxLength={GROUP_RULE.LOCATION.MAX_LENGTH}
        />
      </S.LabelContainer>
    </Container>
  );
}

export default memo(forwardRef(Step6));
