import { ERROR_MESSAGE } from 'constants/message';
import useSnackbar from 'hooks/useSnackbar';

const useHandleError = () => {
  type CustomErrorType = { response: { data: { message: string } } };

  const { setMessage } = useSnackbar();

  const handleError = (error: CustomErrorType) => {
    type ErrorPrefixKey = keyof typeof ERROR_MESSAGE;

    const { message } = error.response.data;
    const splitedMessage = message.split('_');
    const prefix = splitedMessage[0] as ErrorPrefixKey;

    // @ts-ignore
    if (prefix === 'SERVER' || !ERROR_MESSAGE[prefix][message]) {
      setMessage(ERROR_MESSAGE.SERVER.UNHANDLED);
      return;
    }

    // @ts-ignore
    setMessage(ERROR_MESSAGE[prefix][message]);
  };
  return { handleError };
};

export default useHandleError;
