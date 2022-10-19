import { useRecoilValue } from 'recoil';

import useCategory from 'hooks/useCategory';
import useModal from 'hooks/useModal';
import { loginState } from 'store/states';
import { GroupDetailData, GroupParticipants } from 'types/data';

import { Image } from '../@shared/index.styled';
import Content from './Content';

interface MobileProps {
  id: number;
  data: GroupDetailData;
  participants: GroupParticipants;
}

function Mobile({ id, data, participants }: MobileProps) {
  const { user } = useRecoilValue(loginState);
  const categories = useCategory();

  const { showThumbnailModal } = useModal();

  const canEdit = user?.id === data.host.id && !data.finished;

  const showModalToHost = () => {
    if (!canEdit) return;

    showThumbnailModal();
  };

  return (
    <>
      <Image
        src={data.imageUrl ?? categories[data.categoryId].imageUrl}
        canEdit={canEdit}
        onClick={showModalToHost}
      />
      <Content id={id} data={data} participants={participants} />
    </>
  );
}

export default Mobile;
