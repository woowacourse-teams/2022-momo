import { useRecoilValue } from 'recoil';

import { CameraSVG } from 'assets/svg';
import useModal from 'hooks/useModal';
import { loginState } from 'store/states';
import theme from 'styles/theme';
import { GroupDetailData, GroupParticipants } from 'types/data';

import { Image, SvgWrapper } from '../@shared/index.styled';
import Content from './Content';

interface MobileProps {
  data: GroupDetailData;
  participants: GroupParticipants;
}

function Mobile({ data, participants }: MobileProps) {
  const { user } = useRecoilValue(loginState);

  const { showThumbnailModal } = useModal();

  const canEdit = user?.id === data.host.id && !data.finished;

  return (
    <>
      <Image src={data.imageUrl} />
      {canEdit && (
        <SvgWrapper onClick={showThumbnailModal}>
          <CameraSVG width={20} height={20} fill={theme.colors.white001} />
        </SvgWrapper>
      )}
      <Content data={data} participants={participants} />
    </>
  );
}

export default Mobile;
