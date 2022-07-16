import axios from 'apis/axios';
import { ERROR_MESSAGE } from 'constants/message';
import { API_PATH } from 'constants/path';

const requestCreateGroup = async () => {
  axios
    .post(API_PATH.GROUP)
    .then(res => {
      // 요청: body에 생성된 모임의 id를 줘!
      return res;
    })
    .catch(() => {
      throw new Error(ERROR_MESSAGE.CREATE.FAILURE_REQUEST);
    });
};

export { requestCreateGroup };
