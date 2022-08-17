import { useParams } from 'react-router-dom';

import { getGroupDetail } from 'apis/request/group';
import { DetailSideBar, DetailContent } from 'components/Detail';
import { QUERY_KEY } from 'constants/key';
import useCategory from 'hooks/useCategory';
import useRecoilQuery from 'hooks/useRecoilQuery';
import { groupDetailState } from 'store/states';

import * as S from './index.styled';

function Detail() {
  const { id } = useParams();

  const { state: data } = useRecoilQuery(
    groupDetailState,
    QUERY_KEY.GROUP_DETAILS,
    () => getGroupDetail(Number(id)),
    0,
  );
  const { categories } = useCategory();

  return (
    <S.PageContainer>
      {data && (
        <>
          <DetailSideBar
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
          <DetailContent
            name={data.name}
            deadline={data.deadline}
            finished={data.finished}
            categoryId={data.categoryId}
            description={data.description}
          />
        </>
      )}
    </S.PageContainer>
  );
}

export default Detail;
