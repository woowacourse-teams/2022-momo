import React, { forwardRef, LegacyRef, memo } from 'react';

import { GROUP_RULE } from 'constants/rule';
import { CreateStateReturnValues } from 'hooks/useCreateState';

import {
  Container,
  ErrorColor,
  Heading,
  Input,
  LabelContainer,
  Label,
} from '../@shared/styled';

interface Step1Props {
  useNameState: CreateStateReturnValues['useNameState'];
  pressEnterToNext: (e: React.KeyboardEvent<HTMLInputElement>) => void;
}

function Step1(
  { useNameState, pressEnterToNext }: Step1Props,
  ref: LegacyRef<HTMLDivElement>,
) {
  const { name, setName } = useNameState();

  return (
    <Container ref={ref}>
      <Heading>
        모임의 <span>이름</span>을 알려주세요. <ErrorColor>*</ErrorColor>
      </Heading>
      <LabelContainer>
        <Label>
          <p>이름</p>
          <p>
            {name.length}/{GROUP_RULE.NAME.MAX_LENGTH}
          </p>
        </Label>
        <Input
          type="text"
          value={name}
          onChange={setName}
          onKeyPress={pressEnterToNext}
          placeholder="정체를 밝혀라 @_@"
          maxLength={GROUP_RULE.NAME.MAX_LENGTH}
          autoFocus
        />
      </LabelContainer>
    </Container>
  );
}

export default memo(forwardRef(Step1));
