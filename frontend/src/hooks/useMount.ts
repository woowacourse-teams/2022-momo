import { useEffect } from 'react';

const useMount = (effect: React.EffectCallback): void => {
  // eslint-disable-next-line react-hooks/exhaustive-deps
  useEffect(effect, []);
};

export default useMount;
