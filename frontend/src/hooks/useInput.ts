import { useState } from 'react';

interface UseInputReturnType {
  value: string;
  setValue: (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) => void;
  dangerouslySetValue: React.Dispatch<React.SetStateAction<string>>;
}

const useInput = (initialState: string): UseInputReturnType => {
  const [value, setValue] = useState<string>(initialState);

  const changeValue = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ): void => {
    setValue(e.target.value);
  };

  return { value, setValue: changeValue, dangerouslySetValue: setValue };
};

export default useInput;
