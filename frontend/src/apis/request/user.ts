import axios from 'apis/axios';
import { ERROR_MESSAGE } from 'constants/message';
import { API_PATH } from 'constants/path';
import { UserInfo } from 'types/user';

const getUserInfo = () => {
  const accessToken = sessionStorage.getItem('accessToken') ?? '';

  return axios
    .get<UserInfo>(API_PATH.MEMBERS, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
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
  const accessToken = sessionStorage.getItem('accessToken') ?? '';

  return axios.patch(
    API_PATH.NAME,
    { name: data },
    {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );
};

const requestChangePassword = (data: string) => {
  const accessToken = sessionStorage.getItem('accessToken') ?? '';

  return axios.patch(
    API_PATH.PASSWORD,
    { password: data },
    {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );
};

const requestWithdrawal = () => {
  const accessToken = sessionStorage.getItem('accessToken') ?? '';

  return axios.delete(API_PATH.MEMBERS, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
};

export {
  getUserInfo,
  requestChangeName,
  requestChangePassword,
  requestWithdrawal,
};
