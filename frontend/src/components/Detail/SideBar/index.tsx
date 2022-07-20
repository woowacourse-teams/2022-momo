import { CategoryType, DetailData } from 'types/data';

import * as S from './index.styled';
import Info from './Info';
import Participants from './Participants';

function DetailSideBar({
  id,
  name,
  schedules,
  location,
  categoryName,
}: Pick<DetailData, 'id' | 'name' | 'schedules' | 'location'> & {
  categoryName: CategoryType['name'];
}) {
  return (
    <S.Container>
      <Info
        id={id}
        name={name}
        schedules={schedules}
        location={location}
        categoryName={categoryName}
      />
      <Participants />
    </S.Container>
  );
}

export default DetailSideBar;
