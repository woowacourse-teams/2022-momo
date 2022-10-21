import { memo } from 'react';

import { Address } from 'react-daum-postcode';

import Postcode from 'components/Postcode';
import { GROUP_RULE } from 'constants/rule';
import { CreateStateReturnValues } from 'hooks/useCreateState';
import useModal from 'hooks/useModal';

import {
  Container,
  Heading,
  Input,
  LabelContainer,
  Label,
  SectionContainer,
} from '../@shared/styled';
import * as S from './index.styled';

interface Step4Props {
  useCapacityState: CreateStateReturnValues['useCapacityState'];
  useLocationState: CreateStateReturnValues['useLocationState'];
  pressEnterToNext: (e: React.KeyboardEvent<HTMLInputElement>) => void;
}

function Step4({
  useCapacityState,
  useLocationState,
  pressEnterToNext,
}: Step4Props) {
  const { capacity, setCapacity, blurCapacity } = useCapacityState();
  const { location, setLocationAddress, setLocationDetail } =
    useLocationState();

  const { showPostcodeModal } = useModal();

  const setLocation = (data: Address) => {
    setLocationAddress(data.address, data.buildingName, '');
  };

  const isNotFulfilledAddress = location.address === '';

  const preventInputNaN = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (!(e.target instanceof HTMLInputElement)) {
      return;
    }

    if (e.nativeEvent.key === 'Enter') {
      pressEnterToNext(e);
      return;
    }

    if (
      [
        'Backspace',
        'Delete',
        'ArrowLeft',
        'ArrowRight',
        'ArrowUp',
        'ArrowDown',
      ].includes(e.nativeEvent.key)
    ) {
      return;
    }

    if (e.nativeEvent.key === 'Process') {
      e.target.value = capacity.toString();
      return;
    }

    if (e.nativeEvent.key.match(/[^0-9]/g)) {
      e.preventDefault();
    }
  };

  return (
    <Container>
      <SectionContainer>
        <Heading>
          <span>어디에서</span> 모일까요?
        </Heading>
        <S.InputContainer>
          <S.Label>
            <S.Name>주소</S.Name>
            <S.Input
              type="text"
              value={
                location.buildingName ? location.buildingName : location.address
              }
              onClick={showPostcodeModal}
              placeholder="한국루터회관"
              maxLength={GROUP_RULE.LOCATION.MAX_LENGTH}
              readOnly
            />
          </S.Label>
          <S.Label>
            <S.Name>상세</S.Name>
            <Input
              type="text"
              value={location.detail}
              onChange={setLocationDetail}
              title={isNotFulfilledAddress ? '주소를 먼저 입력해주세요.' : ''}
              placeholder="14층"
              maxLength={GROUP_RULE.LOCATION.MAX_LENGTH}
              disabled={isNotFulfilledAddress}
            />
          </S.Label>
        </S.InputContainer>
        <Postcode completeFunc={setLocation} />
      </SectionContainer>
      <SectionContainer>
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
            onBlur={blurCapacity}
            onKeyDown={preventInputNaN}
            placeholder={GROUP_RULE.CAPACITY.MAX.toString()}
          />
        </LabelContainer>
      </SectionContainer>
    </Container>
  );
}

export default memo(Step4);
