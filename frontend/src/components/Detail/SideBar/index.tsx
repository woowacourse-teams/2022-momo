import { CategoryType, DetailData } from 'types/data';

import * as S from './index.styled';
import Info from './Info';
import Participants from './Participants';

function DetailSideBar({
  name,
  schedules,
  location,
  categoryName,
}: Pick<DetailData, 'name' | 'schedules' | 'location'> & {
  categoryName: CategoryType['name'];
}) {
  return (
    <S.Container>
      <Info
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
