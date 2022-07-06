import { ReactComponent as CrownSVG } from 'assets/crown.svg';
import PersonSVG from 'components/svg/Person';

import * as S from './index.styled';

const host = '4기 이프';

const participants = [
  '4기 유세지',
  '4기 하리',
  '4기 유콩',
  '4기 렉스',
  '4기 라쿤',
];

function Participants() {
  return (
    <S.Container>
      <S.Header>참여자 목록</S.Header>
      <S.Box>
        <S.Wrapper>
          <CrownSVG width={32} />
          <S.HostText>{host}</S.HostText>
        </S.Wrapper>
        {participants.map(participant => (
          <S.Wrapper key={participant}>
            <PersonSVG width={32} />
            <S.Text>{participant}</S.Text>
          </S.Wrapper>
        ))}
      </S.Box>
    </S.Container>
  );
}

export default Participants;
