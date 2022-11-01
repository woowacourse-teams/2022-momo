import { SERVER_ERROR_MESSAGE } from 'constants/message';
import useSnackbar from 'hooks/useSnackbar';
import { ServerErrorType } from 'types/data';
import { ObjectKey } from 'types/utils';

interface UseHandleErrorReturnType {
  handleError: (error: ServerErrorType) => void;
}

const useHandleError = (): UseHandleErrorReturnType => {
  const { setMessage } = useSnackbar();

  const handleError = (error: ServerErrorType): void => {
    const { message } = error.response.data;
    const splitedMessage = message.split('_');
    const prefix = splitedMessage[0] as ObjectKey<typeof SERVER_ERROR_MESSAGE>;

    // @ts-ignore
    if (prefix === 'SERVER' || !SERVER_ERROR_MESSAGE[prefix][message]) {
      setMessage(SERVER_ERROR_MESSAGE.SERVER.UNHANDLED, true);
      return;
    }

    // @ts-ignore
    setMessage(SERVER_ERROR_MESSAGE[prefix][message], true);
  };
  return { handleError };
};

export default useHandleError;
