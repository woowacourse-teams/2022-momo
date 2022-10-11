import { useEffect } from 'react';

import { useQuery, useQueryClient } from 'react-query';
import { useParams } from 'react-router-dom';

import {
  requestGroupDetail,
  requestGroupParticipants,
} from 'apis/request/group';
import { QUERY_KEY } from 'constants/key';
import useRecoilQuery from 'hooks/useRecoilQuery';
import { groupDetailState } from 'store/states';
import theme from 'styles/theme';
import { GroupParticipants } from 'types/data';
import { accessTokenProvider } from 'utils/token';

import Desktop from './Versions/Desktop';
import Mobile from './Versions/Mobile';

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

  const queryClient = useQueryClient();

  useEffect(() => {
    queryClient.invalidateQueries(QUERY_KEY.GROUP_DETAILS);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [accessTokenProvider.get()]);

  if (!participants) return <></>;

  if (document.body.clientWidth > theme.breakpoints.md) {
    return <Desktop id={Number(id)} data={data} participants={participants} />;
  }

  return <Mobile id={Number(id)} data={data} participants={participants} />;
}

export default Detail;
