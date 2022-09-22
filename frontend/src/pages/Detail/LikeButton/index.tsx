import { memo, useState } from 'react';

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
}

function LikeButton({ like, id }: LikeButtonProps) {
  const { setMessage } = useSnackbar();
  const { throttle } = useThrottle();
  const { isLogin } = useAuth();
  const [liked, setLiked] = useState(false);

  const toggleLiked = () => {
    if (!isLogin) {
      setMessage(GUIDE_MESSAGE.AUTH.NEED_LOGIN);
      return;
    }

    if (like) {
      requestUnlikeGroup(id)
        .then(() => {
          setLiked(false);
        })
        .catch(() => {
          setMessage(ERROR_MESSAGE.GROUP.FAILURE_LIKE_GROUP);
        });
      return;
    }

    requestLikeGroup(id)
      .then(() => {
        setLiked(true);
      })
      .catch(() => {
        setMessage(ERROR_MESSAGE.GROUP.FAILURE_LIKE_GROUP);
      });
  };

  return (
    <>
      <S.Button type="button" onClick={() => throttle(toggleLiked, likeDelay)}>
        {liked ? <FilledHeartSVG /> : <EmptyHeartSVG />}
      </S.Button>
    </>
  );
}

export default memo(LikeButton);
