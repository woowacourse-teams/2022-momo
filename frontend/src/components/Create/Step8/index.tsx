import { memo, LegacyRef, forwardRef } from 'react';

import { GROUP_RULE } from 'constants/rule';

import { Container, Heading } from '../@shared/styled';
import * as S from './index.styled';

interface Step7Props {
  useDescriptionState: () => {
    description: string;
    setDescription: (description: string) => void;
  };
}

function Step7(
  { useDescriptionState }: Step7Props,
  ref: LegacyRef<HTMLDivElement>,
) {
  const { description, setDescription } = useDescriptionState();
  const changeDescription = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setDescription(e.target.value);
  };

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
          onChange={changeDescription}
          maxLength={GROUP_RULE.DESCRIPTION.MAX_LENGTH}
        />
      </S.LabelContainer>
    </Container>
  );
}

export default memo(forwardRef(Step7));