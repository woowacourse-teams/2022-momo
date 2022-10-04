import { memo, forwardRef, LegacyRef } from 'react';

import { Address } from 'react-daum-postcode';

import Postcode from 'components/Postcode';
import { GROUP_RULE } from 'constants/rule';
import { CreateStateReturnValues } from 'hooks/useCreateState';
import useModal from 'hooks/useModal';

import { Container, Heading } from '../@shared/styled';
import * as S from './index.styled';

interface Step7Props {
  useLocationState: CreateStateReturnValues['useLocationState'];
}

function Step7(
  { useLocationState }: Step7Props,
  ref: LegacyRef<HTMLDivElement>,
) {
  const { location, setLocationAddress, setLocationDetail } =
    useLocationState();

  const { showPostcodeModal } = useModal();

  const setLocation = (data: Address) => {
    setLocationAddress(data.address, data.buildingName, '');
  };

  const isNotFulfilledAddress = location.address === '';

  return (
    <Container ref={ref}>
      <Heading>
        <span>어디에서</span> 모일까요?
      </Heading>
      <S.InputContainer>
        <S.Label>
          주소
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
          상세
          <S.Input
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
    </Container>
  );
}

export default memo(forwardRef(Step7));
