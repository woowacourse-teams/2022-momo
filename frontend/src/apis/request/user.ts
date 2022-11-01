import { AxiosResponse } from 'axios';

import { axiosWithAccessToken } from 'apis/axios';
import { CLIENT_ERROR_MESSAGE } from 'constants/message';
import { API_PATH } from 'constants/path';
import { UserProfile } from 'types/user';

const requestUserInfo = (): Promise<UserProfile> => {
  return axiosWithAccessToken
    .get<UserProfile>(API_PATH.MEMBER.BASE)
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

const requestChangeName = (name: string): Promise<AxiosResponse<null>> => {
  return axiosWithAccessToken.patch(API_PATH.MEMBER.NAME, { name });
};

const requestChangePassword = (
  oldPassword: string,
  newPassword: string,
): Promise<AxiosResponse<null>> => {
  return axiosWithAccessToken.patch(API_PATH.MEMBER.PASSWORD, {
    oldPassword,
    newPassword,
  });
};

const requestWithdrawal = (): Promise<AxiosResponse<null>> => {
  return axiosWithAccessToken.delete(API_PATH.MEMBER.BASE);
};

export {
  requestUserInfo,
  requestChangeName,
  requestChangePassword,
  requestWithdrawal,
};
