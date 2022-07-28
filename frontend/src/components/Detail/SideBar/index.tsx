import { CategoryType, DetailData } from 'types/data';

import * as S from './index.styled';
import Info from './Info';
import Participants from './Participants';

function DetailSideBar({
  id,
  name,
  duration,
  location,
  categoryName,
}: Pick<DetailData, 'id' | 'name' | 'duration' | 'location'> & {
  categoryName: CategoryType['name'];
}) {
  return (
    <S.Container>
      <Info
        id={id}
        name={name}
        duration={duration}
        location={location}
        categoryName={categoryName}
      />
      <Participants id={id} hostName={name} />
    </S.Container>
  );
}

export default DetailSideBar;
