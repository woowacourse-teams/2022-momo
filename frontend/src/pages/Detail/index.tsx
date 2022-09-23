import { useEffect } from 'react';

import { useQueryClient } from 'react-query';
import { useParams } from 'react-router-dom';

import { requestGroupDetail } from 'apis/request/group';
import { QUERY_KEY } from 'constants/key';
import useCategory from 'hooks/useCategory';
import useRecoilQuery from 'hooks/useRecoilQuery';
import { groupDetailState } from 'store/states';
import { accessTokenProvider } from 'utils/token';

import Content from './Content';
import * as S from './index.styled';
import LikeButton from './LikeButton';
import SideBar from './SideBar';

function Detail() {
  const { id } = useParams();

  const { state: data, refetch } = useRecoilQuery(
    groupDetailState,
    QUERY_KEY.GROUP_DETAILS,
    () => requestGroupDetail(Number(id)),
    0,
  );
  const { categories } = useCategory();
  const queryClient = useQueryClient();

  useEffect(() => {
    queryClient.invalidateQueries([QUERY_KEY.GROUP_DETAILS]);
  }, [accessTokenProvider.get()]);

  return (
    <S.PageContainer>
      {data && (
        <>
          <SideBar
            id={Number(id)}
            host={data.host}
            capacity={data.capacity}
            duration={data.duration}
            schedules={data.schedules}
            finished={data.finished}
            location={data.location}
            categoryName={
              categories.find(category => category.id === data.categoryId)
                ?.name || ''
            }
          />
          <Content
            name={data.name}
            deadline={data.deadline}
            finished={data.finished}
            categoryId={data.categoryId}
            location={data.location}
            description={data.description}
          />
          <LikeButton id={Number(id)} like={data.like} refetch={refetch} />
        </>
      )}
    </S.PageContainer>
  );
}

export default Detail;
