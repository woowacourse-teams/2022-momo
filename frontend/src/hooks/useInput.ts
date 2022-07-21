import { useState } from 'react';

const useInput = (initialState: string) => {
  const [value, setValue] = useState(initialState);

  const changeValue = (e: React.ChangeEvent<HTMLInputElement>) => {
    setValue(e.target.value);
  };

  return { value, setValue: changeValue };
};

export default useInput;
