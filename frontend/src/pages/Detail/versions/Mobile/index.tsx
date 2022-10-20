import { useRecoilValue } from 'recoil';

import useModal from 'hooks/useModal';
import { loginState } from 'store/states';
import { GroupDetailData, GroupParticipants } from 'types/data';

import { Image } from '../@shared/index.styled';
import Content from './Content';

interface MobileProps {
  data: GroupDetailData;
  participants: GroupParticipants;
}

function Mobile({ data, participants }: MobileProps) {
  const { user } = useRecoilValue(loginState);

  const { showThumbnailModal } = useModal();

  const canEdit = user?.id === data.host.id && !data.finished;

  const showModalToHost = () => {
    if (!canEdit) return;

    showThumbnailModal();
  };

  return (
    <>
      <Image src={data.imageUrl} canEdit={canEdit} onClick={showModalToHost} />
      <Content data={data} participants={participants} />
    </>
  );
}

export default Mobile;
