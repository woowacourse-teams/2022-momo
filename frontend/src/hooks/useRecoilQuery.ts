import { useEffect } from 'react';

import { useQuery } from 'react-query';
import { RecoilState, useRecoilState } from 'recoil';

const useRecoilQuery = <T>(
  recoilState: RecoilState<T>,
  key: string,
  getFunc: () => Promise<T>,
  staleTime = Infinity,
  suspense = true,
) => {
  const [state, setState] = useRecoilState(recoilState);
  const { isLoading, isError, data, error } = useQuery(key, getFunc, {
    staleTime,
    suspense,
  });

  // TODO: React Query + Recoil -> 좀 더 우아한 방법을 찾아보자
  useEffect(() => {
    if (!data) return;

    setState(data);
  }, [data, setState]);

  return { state, isLoading, isError, error };
};

export default useRecoilQuery;
