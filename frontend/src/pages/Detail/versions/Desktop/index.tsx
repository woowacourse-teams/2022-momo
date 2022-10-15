import { GroupDetailData, GroupParticipants } from 'types/data';
import { getCategoryImage } from 'utils/category';

import { Image } from '../@shared/index.styled';
import Content from './Content';

interface DesktopProps {
  id: GroupDetailData['id'];
  data: GroupDetailData;
  participants: GroupParticipants;
}

function Desktop({ id, data, participants }: DesktopProps) {
  return (
    <>
      <Image src={getCategoryImage(data.categoryId)} />
      <Content id={id} data={data} participants={participants} />
    </>
  );
}

export default Desktop;
