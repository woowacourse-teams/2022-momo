import { memo } from 'react';

import { useQueryClient } from 'react-query';

import { requestLikeGroup, requestUnlikeGroup } from 'apis/request/group';
import { EmptyHeartSVG, FilledHeartSVG } from 'assets/svg';
import { QUERY_KEY } from 'constants/key';
import { CLIENT_ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import useAuth from 'hooks/useAuth';
import useHandleError from 'hooks/useHandleError';
import useSnackbar from 'hooks/useSnackbar';
import useThrottle from 'hooks/useThrottle';
import { GroupDetailData } from 'types/data';

import * as S from './index.styled';

const svgSize = '1.5rem';
const likeDelay = 1000;

interface LikeButtonProps {
  like: GroupDetailData['like'];
  id: number;
}

function LikeButton({ like, id }: LikeButtonProps) {
  const { isLogin } = useAuth();

  const { setMessage } = useSnackbar();
  const { handleError } = useHandleError();

  const queryClient = useQueryClient();
  const refetch = () => {
    queryClient.invalidateQueries(QUERY_KEY.GROUP_DETAILS);
  };

  const toggleLiked = () => {
    if (!isLogin) {
      setMessage(GUIDE_MESSAGE.AUTH.NEED_LOGIN, true);
      return;
    }

    if (like) {
      requestUnlikeGroup(id)
        .then(() => {
          setMessage(GUIDE_MESSAGE.GROUP.SUCCESS_UNLIKE_GROUP);
          refetch();
        })
        .catch(error => {
          if (!error) {
            setMessage(CLIENT_ERROR_MESSAGE.GROUP.FAILURE_LIKE_GROUP, true);
          }

          handleError(error);
        });
      return;
    }

    requestLikeGroup(id)
      .then(() => {
        setMessage(GUIDE_MESSAGE.GROUP.SUCCESS_LIKE_GROUP);
        refetch();
      })
      .catch(error => {
        if (!error) {
          setMessage(CLIENT_ERROR_MESSAGE.GROUP.FAILURE_LIKE_GROUP, true);
        }
        handleError(error);
      });
  };

  const throttledToggleLiked = useThrottle(toggleLiked, likeDelay);

  return (
    <>
      <S.Button type="button" onClick={throttledToggleLiked}>
        {like ? (
          <FilledHeartSVG width={svgSize} />
        ) : (
          <EmptyHeartSVG width={svgSize} />
        )}
      </S.Button>
    </>
  );
}

export default memo(LikeButton);
