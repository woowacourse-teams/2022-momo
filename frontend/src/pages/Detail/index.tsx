import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import { getGroupDetail } from 'apis/request/group';
import { DetailSideBar, DetailContent } from 'components/Detail';
import { QUERY_KEY } from 'constants/key';
import useCategory from 'hooks/useCategory';

import * as S from './index.styled';

function Detail() {
  const { id } = useParams();

  const {
    data,
    isLoading: isGroupDetailLoading,
    isError: isGroupDetailError,
  } = useQuery(QUERY_KEY.GROUP_DETAILS, () => getGroupDetail(Number(id)));
  const {
    categories,
    isLoading: isCategoryLoading,
    isError: isCategoryError,
  } = useCategory();

  if (isGroupDetailLoading || isCategoryLoading)
    return <h2>잠시만 기다려주세요... 🔎</h2>;

  if (isGroupDetailError || isCategoryError)
    return <h2>에러가 발생했습니다!!!! 👿</h2>;

  return (
    <S.PageContainer>
      {data && (
        <>
          <DetailSideBar
            id={Number(id)}
            name={data.host.name}
            schedules={data.schedules}
            location={data.location}
            categoryName={
              categories.find(category => category.id === data.categoryId)
                ?.name || ''
            }
          />
          <DetailContent
            name={data.name}
            deadline={data.deadline}
            categoryId={data.categoryId}
            description={data.description}
          />
        </>
      )}
    </S.PageContainer>
  );
}

export default Detail;
