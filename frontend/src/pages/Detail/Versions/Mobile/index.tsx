import useCategory from 'hooks/useCategory';
import Description from 'pages/Detail/Description';
import Location from 'pages/Detail/Location';
import Participants from 'pages/Detail/Participants';
import Schedule from 'pages/Detail/Schedule';
import { GroupDetailData, GroupParticipants } from 'types/data';
import { getCategoryImage } from 'utils/category';
import { convertDeadlineToRemainTime } from 'utils/date';

import { Image, Category, Title, Duration } from '../@shared/index.styled';
import ControlButton from './ControlButton';
import * as S from './index.styled';
import LikeButton from './LikeButton';

interface MobileProps {
  id: number;
  data: GroupDetailData;
  participants: GroupParticipants;
}

function Mobile({ id, data, participants }: MobileProps) {
  const { categories } = useCategory();

  return (
    <>
      <Image src={getCategoryImage(data.categoryId)} />
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
          <S.SubMenu>
            <LikeButton id={id} like={data.like} />
          </S.SubMenu>
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
    </>
  );
}

export default Mobile;
