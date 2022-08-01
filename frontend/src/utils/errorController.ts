import { ERROR_MESSAGE } from 'constants/message';

const showErrorMessage = (message: string) => {
  switch (message) {
    case 'SIGNUP_ERROR_001':
      alert(ERROR_MESSAGE.SIGNUP.INVALID_ID);
      return;
    case 'SIGNUP_ERROR_002':
      alert(ERROR_MESSAGE.SIGNUP.INVALID_PASSWORD);
      return;
    case 'SIGNUP_ERROR_003':
      alert(ERROR_MESSAGE.SIGNUP.DUPLICATED_ID);
      return;
    case 'LOGIN_ERROR_001':
      alert(ERROR_MESSAGE.AUTH.NOT_EXIST_ID);
      return;
    case 'LOGIN_ERROR_002':
      alert(ERROR_MESSAGE.AUTH.INCORRECT_PASSWORD);
      return;
  }
};

export { showErrorMessage };
