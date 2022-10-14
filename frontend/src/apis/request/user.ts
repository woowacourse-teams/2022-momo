import { axiosWithAccessToken } from 'apis/axios';
import { CLIENT_ERROR_MESSAGE } from 'constants/message';
import { API_PATH } from 'constants/path';
import { UserProfile } from 'types/user';

const requestUserInfo = () => {
  return axiosWithAccessToken
    .get<UserProfile>(API_PATH.MEMBERS)
    .then(response => {
      return response.data;
    })
    .catch(error => {
      if (!error) {
        throw new Error(CLIENT_ERROR_MESSAGE.MEMBER.FAILURE_REQUEST);
      }
      throw error;
    });
};

const requestChangeName = (name: string) => {
  return axiosWithAccessToken.patch(API_PATH.NAME, { name });
};

const requestChangePassword = (oldPassword: string, newPassword: string) => {
  return axiosWithAccessToken.patch(API_PATH.PASSWORD, {
    oldPassword,
    newPassword,
  });
};

const requestWithdrawal = () => {
  return axiosWithAccessToken.delete(API_PATH.MEMBERS);
};

export {
  requestUserInfo,
  requestChangeName,
  requestChangePassword,
  requestWithdrawal,
};
