import * as S from './index.styled';
import Info from './Info';
import Participants from './Participants';

function DetailSideBar() {
  return (
    <S.Container>
      <Info />
      <Participants />
    </S.Container>
  );
}

export default DetailSideBar;
