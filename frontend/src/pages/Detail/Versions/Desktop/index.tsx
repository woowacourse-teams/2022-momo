import useCategory from 'hooks/useCategory';
import { GroupDetailData, GroupParticipants } from 'types/data';
import { getCategoryImage } from 'utils/category';
import { convertDeadlineToRemainTime } from 'utils/date';

import { Image, Category, Title, Duration } from '../@shared/index.styled';
import Content from './Content';
import ControlButton from './ControlButton';
import * as S from './index.styled';
import LikeButton from './LikeButton';
import SideBar from './SideBar';

interface DesktopProps {
  id: number;
  data: GroupDetailData;
  participants: GroupParticipants;
}

function Desktop({ id, data, participants }: DesktopProps) {
  const { categories } = useCategory();

  return (
    <>
      <Image src={getCategoryImage(data.categoryId)} />
      <S.Container>
        <S.StickyContainer>
          <S.Header>
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
          <Content location={data.location} description={data.description} />
          <SideBar
            host={data.host}
            capacity={data.capacity}
            duration={data.duration}
            schedules={data.schedules}
            participants={participants}
          />
          <LikeButton id={id} like={data.like} />
        </S.ContentContainer>
      </S.Container>
    </>
  );
}

export default Desktop;
