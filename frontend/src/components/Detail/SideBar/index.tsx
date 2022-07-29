import { CategoryType, DetailData } from 'types/data';

import * as S from './index.styled';
import Info from './Info';
import Participants from './Participants';

function DetailSideBar({
  id,
  name,
  capacity,
  duration,
  location,
  categoryName,
}: Pick<DetailData, 'id' | 'name' | 'capacity' | 'duration' | 'location'> & {
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
      <Participants id={id} hostName={name} capacity={capacity} />
    </S.Container>
  );
}

export default DetailSideBar;
