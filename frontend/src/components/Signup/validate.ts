import { CLIENT_ERROR_MESSAGE } from 'constants/message';
import { MEMBER_RULE } from 'constants/rule';

interface isValidSignupFormDataProp {
  inputId: string;
  isValidName: boolean;
  isValidPassword: boolean;
  isValidConfirmPassword: boolean;
}

const passwordRegExp =
  '^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$';

const checkValidId = (inputId: string) => {
  return (
    inputId.length >= MEMBER_RULE.ID.MIN_LENGTH &&
    inputId.length <= MEMBER_RULE.ID.MAX_LENGTH
  );
};

const checkValidName = (name: string) => {
  return (
    name.length >= MEMBER_RULE.NAME.MIN_LENGTH &&
    name.length <= MEMBER_RULE.NAME.MAX_LENGTH
  );
};

const checkValidPassword = (password: string) => {
  const regex = new RegExp(passwordRegExp);

  return regex.test(password);
};

const isValidSignupFormData = ({
  inputId,
  isValidName,
  isValidPassword,
  isValidConfirmPassword,
}: isValidSignupFormDataProp) => {
  if (!checkValidId(inputId)) {
    throw new Error(CLIENT_ERROR_MESSAGE.SIGNUP.INVALID_ID);
  }

  if (!isValidName) {
    throw new Error(CLIENT_ERROR_MESSAGE.SIGNUP.INVALID_NICKNAME);
  }

  if (!isValidPassword) {
    throw new Error(CLIENT_ERROR_MESSAGE.SIGNUP.INVALID_PASSWORD);
  }

  if (!isValidConfirmPassword) {
    throw new Error(CLIENT_ERROR_MESSAGE.SIGNUP.INVALID_CONFIRMPASSWORD);
  }
};

export {
  checkValidId,
  checkValidName,
  checkValidPassword,
  isValidSignupFormData,
};
