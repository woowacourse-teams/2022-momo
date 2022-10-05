import { memo } from 'react';

import {
  QueryObserverResult,
  RefetchOptions,
  RefetchQueryFilters,
} from 'react-query';

import { requestLikeGroup, requestUnlikeGroup } from 'apis/request/group';
import { EmptyHeartSVG, FilledHeartSVG } from 'assets/svg';
import { ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import useAuth from 'hooks/useAuth';
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
  const { isLogin } = useAuth();

  const toggleLiked = () => {
    if (!isLogin) {
      setMessage(GUIDE_MESSAGE.AUTH.NEED_LOGIN);
      return;
    }

    if (like) {
      requestUnlikeGroup(id)
        .then(() => {
          refetch();
        })
        .catch(() => {
          setMessage(ERROR_MESSAGE.GROUP.FAILURE_LIKE_GROUP);
        });
      return;
    }

    requestLikeGroup(id)
      .then(() => {
        refetch();
      })
      .catch(() => {
        setMessage(ERROR_MESSAGE.GROUP.FAILURE_LIKE_GROUP);
      });
  };

  const throttledToggleLiked = useThrottle(toggleLiked, likeDelay);

  return (
    <>
      <S.Button type="button" onClick={throttledToggleLiked}>
        {like ? <FilledHeartSVG /> : <EmptyHeartSVG />}
      </S.Button>
    </>
  );
}

export default memo(LikeButton);
