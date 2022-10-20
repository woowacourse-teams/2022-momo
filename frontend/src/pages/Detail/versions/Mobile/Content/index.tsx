import { useState } from 'react';

import { useRecoilValue } from 'recoil';

import { PencilSVG } from 'assets/svg';
import useCategory from 'hooks/useCategory';
import Description from 'pages/Detail/Description';
import Location from 'pages/Detail/Location';
import Participants from 'pages/Detail/Participants';
import Schedule from 'pages/Detail/Schedule';
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
            <Duration>
              모집{' '}
              {data.finished
                ? '마감 완료'
                : convertDeadlineToRemainTime(data.deadline)}
            </Duration>
            <Title>{data.name}</Title>
            <Category>
              {categories.find(category => category.id === data.categoryId)
                ?.name || ''}
            </Category>
          </S.TitleContainer>
          <S.SideMenu>
            <LikeButton id={id} like={data.like} />
            {user?.id === data.host.id && !data.finished && (
              <PencilSVG
                width={svgSize}
                height={svgSize}
                onClick={showEditMode}
              />
            )}
          </S.SideMenu>
        </S.Header>
      </S.StickyContainer>
      <S.ContentContainer>
        <Description type="detail">{data.description}</Description>
        <Schedule duration={data.duration} schedules={data.schedules} />
        <Location location={data.location} />
        <Participants
          host={data.host}
          capacity={data.capacity}
          participants={participants}
        />
      </S.ContentContainer>
      <S.ControlContainer>
        <ControlButton
          id={id}
          host={data.host}
          capacity={data.capacity}
          finished={data.finished}
          participants={participants}
        />
      </S.ControlContainer>
    </S.Container>
  );
}

export default Content;
