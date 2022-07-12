import { DetailData } from 'types/data';

import * as S from './index.styled';
import Info from './Info';
import Participants from './Participants';

function DetailSideBar({
  name,
  schedules,
  categoryId,
  location,
}: Pick<DetailData, 'name' | 'schedules' | 'categoryId' | 'location'>) {
  return (
    <S.Container>
      <Info
        name={name}
        schedules={schedules}
        categoryId={categoryId}
        location={location}
      />
      <Participants />
    </S.Container>
  );
}

export default DetailSideBar;
