import { useState } from 'react';

import { useRecoilValue } from 'recoil';

import { PencilSVG } from 'assets/svg';
import useCategory from 'hooks/useCategory';
import {
  Category,
  Duration,
  Title,
} from 'pages/Detail/versions/@shared/index.styled';
import { loginState } from 'store/states';
import { GroupDetailData, GroupParticipants, GroupSummary } from 'types/data';
import { convertDeadlineToRemainTime } from 'utils/date';

import ControlButton from '../ControlButton';
import LikeButton from '../LikeButton';
import EditMode from './EditMode';
import * as S from './index.styled';
import Left from './Left';
import Right from './Right';

const svgSize = 20;

interface ContentProps {
  id: GroupSummary['id'];
  data: GroupDetailData;
  participants: GroupParticipants;
}

function Content({ id, data, participants }: ContentProps) {
  const { user } = useRecoilValue(loginState);
  const categories = useCategory();

  const [mode, setMode] = useState<'basic' | 'edit'>('basic');

  const showEditMode = () => {
    setMode('edit');
  };

  const finishEditMode = () => {
    setMode('basic');
  };

  if (mode === 'edit') {
    return <EditMode id={id} data={data} finishEditMode={finishEditMode} />;
  }

  return (
    <S.Container>
      <S.StickyContainer>
        <S.Header>
          <S.TitleContainer>
            <Category>
              {categories.find(category => category.id === data.categoryId)
                ?.name || ''}
            </Category>
            <Title>{data.name}</Title>
            <Duration>
              모집{' '}
              {data.finished
                ? '마감 완료'
                : convertDeadlineToRemainTime(data.deadline)}
            </Duration>
          </S.TitleContainer>
          {user?.id === data.host.id && !data.finished && (
            <PencilSVG
              width={svgSize}
              height={svgSize}
              onClick={showEditMode}
            />
          )}
        </S.Header>
        <ControlButton
          id={id}
          host={data.host}
          capacity={data.capacity}
          finished={data.finished}
          participants={participants}
        />
      </S.StickyContainer>
      <S.ContentContainer>
        <Left location={data.location} description={data.description} />
        <Right
          host={data.host}
          capacity={data.capacity}
          duration={data.duration}
          schedules={data.schedules}
          participants={participants}
        />
        <LikeButton id={id} like={data.like} />
      </S.ContentContainer>
    </S.Container>
  );
}

export default Content;
