import { ERROR_MESSAGE } from 'constants/message';

const showErrorMessage = (message: string) => {
  switch (message) {
    case 'SIGNUP_ERROR_001':
      return ERROR_MESSAGE.SIGNUP.INVALID_ID;

    case 'SIGNUP_ERROR_002':
      return ERROR_MESSAGE.SIGNUP.INVALID_PASSWORD;

    case 'SIGNUP_ERROR_003':
      return ERROR_MESSAGE.SIGNUP.DUPLICATED_ID;

    case 'LOGIN_ERROR_001':
      return ERROR_MESSAGE.AUTH.NOT_EXIST_ID;

    case 'LOGIN_ERROR_002':
      return ERROR_MESSAGE.AUTH.INCORRECT_PASSWORD;
  }
};

export { showErrorMessage };
