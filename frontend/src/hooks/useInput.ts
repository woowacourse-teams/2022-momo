import { useState } from 'react';

interface UseInputReturnType<T> {
  value: string | T;
  setValue: (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) => void;
  dangerouslySetValue: React.Dispatch<React.SetStateAction<string | T>>;
}

const useInput = <T>(initialState: T): UseInputReturnType<T> => {
  const [value, setValue] = useState<T | string>(initialState);

  const changeValue = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ): void => {
    setValue(e.target.value);
  };

  return { value, setValue: changeValue, dangerouslySetValue: setValue };
};

export default useInput;
