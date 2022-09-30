import { ERROR_MESSAGE } from 'constants/message';

interface isValidSignupFormDataProp {
  isValidName: boolean;
  isValidPassword: boolean;
  isValidConfirmPassword: boolean;
}

const passwordRegExp =
  '^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$';

const checkValidNickname = (nickname: string) => {
  return nickname.length <= 6 && nickname.length >= 1;
};

const checkValidPassword = (password: string) => {
  const regex = new RegExp(passwordRegExp);

  return regex.test(password);
};

const isValidSignupFormData = ({
  isValidName,
  isValidPassword,
  isValidConfirmPassword,
}: isValidSignupFormDataProp) => {
  if (!isValidName) {
    throw new Error(ERROR_MESSAGE.SIGNUP.INVALID_NICKNAME);
  }

  if (!isValidPassword) {
    throw new Error(ERROR_MESSAGE.SIGNUP.SIGNUP_002);
  }

  if (!isValidConfirmPassword) {
    throw new Error(ERROR_MESSAGE.SIGNUP.INVALID_CONFIRMPASSWORD);
  }
};

export { checkValidNickname, checkValidPassword, isValidSignupFormData };
