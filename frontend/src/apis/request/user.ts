import axios from 'apis/axios';
import { ERROR_MESSAGE } from 'constants/message';
import { API_PATH } from 'constants/path';
import { UserProfile } from 'types/user';

const getUserInfo = () => {
  const accessToken = sessionStorage.getItem('accessToken') ?? '';

  return axios
    .get<UserProfile>(API_PATH.MEMBERS, {
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

const requestConfirmPassword = (password: string) => {
  const accessToken = sessionStorage.getItem('accessToken') ?? '';

  return axios.put(
    API_PATH.MEMBERS,
    { password },
    {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );
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
  requestConfirmPassword,
  requestChangeName,
  requestChangePassword,
  requestWithdrawal,
};
