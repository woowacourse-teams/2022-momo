import { useRecoilValue } from 'recoil';

import { CameraSVG } from 'assets/svg';
import useModal from 'hooks/useModal';
import { loginState } from 'store/states';
import theme from 'styles/theme';
import { GroupDetailData, GroupParticipants, GroupSummary } from 'types/data';

import { Image, SvgWrapper } from '../@shared/index.styled';
import Content from './Content';

const svgSize = 20;

interface DesktopProps {
  id: GroupSummary['id'];
  data: GroupDetailData;
  participants: GroupParticipants;
}

function Desktop({ id, data, participants }: DesktopProps) {
  const { user } = useRecoilValue(loginState);

  const { showThumbnailModal } = useModal();

  const canEdit = user?.id === data.host.id && !data.finished;

  return (
    <>
      <Image src={data.imageUrl} />
      {canEdit && (
        <SvgWrapper onClick={showThumbnailModal}>
          <CameraSVG width={svgSize} fill={theme.colors.white001} />
        </SvgWrapper>
      )}
      <Content id={id} data={data} participants={participants} />
    </>
  );
}

export default Desktop;
