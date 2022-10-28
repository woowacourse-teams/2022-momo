import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import {
  requestGroupDetail,
  requestGroupParticipants,
} from 'apis/request/group';
import ImageDropBox from 'components/ImageDropBox';
import { QUERY_KEY } from 'constants/key';
import theme from 'styles/theme';
import { GroupParticipants } from 'types/data';

import Desktop from './versions/Desktop';
import Mobile from './versions/Mobile';

function Detail() {
  const { id } = useParams();

  const { data } = useQuery(QUERY_KEY.GROUP_DETAILS, () =>
    requestGroupDetail(Number(id)),
  );
  const { data: participants } = useQuery<GroupParticipants>(
    `${QUERY_KEY.GROUP_PARTICIPANTS}/${id}`,
    () => requestGroupParticipants(Number(id)),
    { staleTime: Infinity },
  );

  if (!data || !participants) return <></>;

  return (
    <>
      {document.body.clientWidth > theme.breakpoints.md ? (
        <Desktop id={Number(id)} data={data} participants={participants} />
      ) : (
        <Mobile id={Number(id)} data={data} participants={participants} />
      )}
      <ImageDropBox id={Number(id)} />
    </>
  );
}

export default Detail;
