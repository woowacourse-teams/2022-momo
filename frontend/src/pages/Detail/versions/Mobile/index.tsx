import { GroupDetailData, GroupParticipants } from 'types/data';
import { getCategoryImage } from 'utils/category';

import { Image } from '../@shared/index.styled';
import Content from './Content';

interface MobileProps {
  id: number;
  data: GroupDetailData;
  participants: GroupParticipants;
}

function Mobile({ id, data, participants }: MobileProps) {
  return (
    <>
      <Image src={getCategoryImage(data.categoryId)} />
      <Content id={id} data={data} participants={participants} />
    </>
  );
}

export default Mobile;
