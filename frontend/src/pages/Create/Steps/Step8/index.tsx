import { memo, LegacyRef, forwardRef } from 'react';

import { GROUP_RULE } from 'constants/rule';
import { CreateGroupData } from 'types/data';

import { Container, Heading } from '../@shared/styled';
import * as S from './index.styled';

interface Step8Props {
  useDescriptionState: () => {
    description: CreateGroupData['description'];
    setDescription: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
  };
}

function Step8(
  { useDescriptionState }: Step8Props,
  ref: LegacyRef<HTMLDivElement>,
) {
  const { description, setDescription } = useDescriptionState();

  return (
    <Container ref={ref}>
      <Heading>
        <span>모임에 대한 추가적인 설명</span>이 필요하다면 입력해주세요.
        <p>자세히 설명할수록 인원이 모일 확률이 높아져요!</p>
      </Heading>
      <S.LabelContainer>
        <S.Label>
          <p>
            {description.length}/{GROUP_RULE.DESCRIPTION.MAX_LENGTH}
          </p>
        </S.Label>
        <S.TextArea
          value={description}
          onChange={setDescription}
          maxLength={GROUP_RULE.DESCRIPTION.MAX_LENGTH}
        />
      </S.LabelContainer>
    </Container>
  );
}

export default memo(forwardRef(Step8));
