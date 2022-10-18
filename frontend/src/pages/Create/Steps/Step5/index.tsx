import { memo } from 'react';

import { GROUP_RULE } from 'constants/rule';
import { CreateStateReturnValues } from 'hooks/useCreateState';

import { Container, Heading, SectionContainer } from '../@shared/styled';
import * as S from './index.styled';
interface Step5Props {
  useDescriptionState: CreateStateReturnValues['useDescriptionState'];
}

function Step5({ useDescriptionState }: Step5Props) {
  const { description, setDescription } = useDescriptionState();
  return (
    <Container>
      <SectionContainer>
        <Heading>
          모임의 <span>추가 설명</span>을 입력해주세요.
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
      </SectionContainer>
    </Container>
  );
}

export default memo(Step5);
