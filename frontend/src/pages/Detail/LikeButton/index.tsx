import { memo } from 'react';

import {
  QueryObserverResult,
  RefetchOptions,
  RefetchQueryFilters,
} from 'react-query';

import { requestLikeGroup, requestUnlikeGroup } from 'apis/request/group';
import { EmptyHeartSVG, FilledHeartSVG } from 'assets/svg';
import { CLIENT_ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import useAuth from 'hooks/useAuth';
import useHandleError from 'hooks/useHandleError';
import useSnackbar from 'hooks/useSnackbar';
import useThrottle from 'hooks/useThrottle';
import { GroupDetailData } from 'types/data';

import * as S from './index.styled';

const likeDelay = 1000;

interface LikeButtonProps {
  like: GroupDetailData['like'];
  id: number;
  refetch: <TPageData>(
    options?: (RefetchOptions & RefetchQueryFilters<TPageData>) | undefined,
  ) => Promise<QueryObserverResult<GroupDetailData, unknown>>;
}

function LikeButton({ like, id, refetch }: LikeButtonProps) {
  const { setMessage } = useSnackbar();
  const { handleError } = useHandleError();
  const throttle = useThrottle();
  const { isLogin } = useAuth();

  const throttleToggleLiked = () => throttle(toggleLiked, likeDelay);

  const toggleLiked = () => {
    if (!isLogin) {
      setMessage(GUIDE_MESSAGE.AUTH.NEED_LOGIN);
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
            setMessage(CLIENT_ERROR_MESSAGE.GROUP.FAILURE_LIKE_GROUP);
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
          setMessage(CLIENT_ERROR_MESSAGE.GROUP.FAILURE_LIKE_GROUP);
        }
        handleError(error);
      });
  };

  return (
    <>
      <S.Button type="button" onClick={throttleToggleLiked}>
        {like ? <FilledHeartSVG /> : <EmptyHeartSVG />}
      </S.Button>
    </>
  );
}

export default memo(LikeButton);
