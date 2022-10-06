import { useEffect } from 'react';

import { useQuery, useQueryClient } from 'react-query';
import { useParams } from 'react-router-dom';

import {
  requestGroupDetail,
  requestGroupParticipants,
} from 'apis/request/group';
import { QUERY_KEY } from 'constants/key';
import useCategory from 'hooks/useCategory';
import useRecoilQuery from 'hooks/useRecoilQuery';
import { groupDetailState } from 'store/states';
import { GroupParticipants } from 'types/data';
import { getCategoryImage } from 'utils/category';
import { convertDeadlineToRemainTime } from 'utils/date';
import { accessTokenProvider } from 'utils/token';

import Content from './Content';
import ControlButton from './ControlButton';
import * as S from './index.styled';
import LikeButton from './LikeButton';
import SideBar from './SideBar';

function Detail() {
  const { id } = useParams();

  const { state: data } = useRecoilQuery(
    groupDetailState,
    QUERY_KEY.GROUP_DETAILS,
    () => requestGroupDetail(Number(id)),
    0,
  );
  const { data: participants } = useQuery<GroupParticipants>(
    `${QUERY_KEY.GROUP_PARTICIPANTS}/${id}`,
    () => requestGroupParticipants(Number(id)),
    { staleTime: Infinity },
  );

  const { categories } = useCategory();

  const queryClient = useQueryClient();

  useEffect(() => {
    queryClient.invalidateQueries(QUERY_KEY.GROUP_DETAILS);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [accessTokenProvider.get()]);

  return (
    <>
      <S.Image src={getCategoryImage(data.categoryId)} />
      <S.Container>
        {data && participants && (
          <>
            <S.StickyContainer>
              <S.Header>
                <S.Category>
                  {categories.find(category => category.id === data.categoryId)
                    ?.name || ''}
                </S.Category>
                <S.Title>{data.name}</S.Title>
                <S.Duration>
                  모집{' '}
                  {data.finished
                    ? '마감 완료'
                    : convertDeadlineToRemainTime(data.deadline)}
                </S.Duration>
              </S.Header>
              <ControlButton
                id={Number(id)}
                host={data.host}
                capacity={data.capacity}
                finished={data.finished}
                participants={participants}
              />
            </S.StickyContainer>
            <S.ContentContainer>
              <Content
                location={data.location}
                description={data.description}
              />
              <SideBar
                host={data.host}
                capacity={data.capacity}
                duration={data.duration}
                schedules={data.schedules}
                participants={participants}
              />
              <LikeButton id={Number(id)} like={data.like} />
            </S.ContentContainer>
          </>
        )}
      </S.Container>
    </>
  );
}

export default Detail;
