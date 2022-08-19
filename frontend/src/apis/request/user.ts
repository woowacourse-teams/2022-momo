import axios from 'apis/axios';
import { ERROR_MESSAGE } from 'constants/message';
import { API_PATH } from 'constants/path';
import { UserProfile } from 'types/user';
import { accessTokenProvider } from 'utils/token';

const getUserInfo = () => {
  return axios
    .get<UserProfile>(API_PATH.MEMBERS, {
      headers: {
        Authorization: `Bearer ${accessTokenProvider.get()}`,
      },
    })
    .then(response => {
      return response.data;
    })
    .catch(() => {
      throw new Error(ERROR_MESSAGE.MEMBER.FAILURE_REQUEST);
    });
};

const requestChangeName = (data: string) => {
  return axios.patch(
    API_PATH.NAME,
    { name: data },
    {
      headers: {
        Authorization: `Bearer ${accessTokenProvider.get()}`,
      },
    },
  );
};

const requestChangePassword = (oldPassword: string, newPassword: string) => {
  return axios.patch(
    API_PATH.PASSWORD,
    { oldPassword, newPassword },
    {
      headers: {
        Authorization: `Bearer ${accessTokenProvider.get()}`,
      },
    },
  );
};

const requestWithdrawal = () => {
  return axios.delete(API_PATH.MEMBERS, {
    headers: {
      Authorization: `Bearer ${accessTokenProvider.get()}`,
    },
  });
};

export {
  getUserInfo,
  requestChangeName,
  requestChangePassword,
  requestWithdrawal,
};
