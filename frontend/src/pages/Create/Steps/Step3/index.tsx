import { forwardRef, LegacyRef, memo } from 'react';

import { GROUP_RULE } from 'constants/rule';
import { CreateGroupData } from 'types/data';

import {
  Container,
  Heading,
  Input,
  Label,
  LabelContainer,
} from '../@shared/styled';

interface Step3Props {
  useCapacityState: () => {
    capacity: CreateGroupData['capacity'];
    setCapacity: (e: React.ChangeEvent<HTMLInputElement>) => void;
  };
  pressEnterToNext: (e: React.KeyboardEvent<HTMLInputElement>) => void;
}

function Step3(
  { useCapacityState, pressEnterToNext }: Step3Props,
  ref: LegacyRef<HTMLDivElement>,
) {
  const { capacity, setCapacity } = useCapacityState();

  return (
    <Container ref={ref}>
      <Heading>
        모임에 <span>최대 몇 명</span>까지 모일 수 있나요?
        <p>미입력 시 {GROUP_RULE.CAPACITY.MAX}명으로 설정됩니다.</p>
      </Heading>
      <LabelContainer>
        <Label>
          <p>최대 인원 수</p>
        </Label>
        <Input
          type="number"
          min={GROUP_RULE.CAPACITY.MIN}
          max={GROUP_RULE.CAPACITY.MAX}
          value={capacity || ''}
          onChange={setCapacity}
          onKeyPress={pressEnterToNext}
          placeholder={GROUP_RULE.CAPACITY.MAX.toString()}
        />
      </LabelContainer>
    </Container>
  );
}

export default memo(forwardRef(Step3));
