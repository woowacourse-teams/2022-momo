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
    alert(ERROR_MESSAGE.SIGNUP.INVALID_NICKNAME);
    return false;
  }

  if (!isValidPassword) {
    alert(ERROR_MESSAGE.SIGNUP.INVALID_PASSWORD);
    return false;
  }

  if (!isValidConfirmPassword) {
    alert(ERROR_MESSAGE.SIGNUP.INVALID_CONFIRMPASSWORD);
    return false;
  }

  return true;
};

export { checkValidNickname, checkValidPassword, isValidSignupFormData };
