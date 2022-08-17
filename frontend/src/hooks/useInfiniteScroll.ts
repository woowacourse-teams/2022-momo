import { useEffect } from 'react';

import {
  QueryObserverResult,
  RefetchOptions,
  RefetchQueryFilters,
} from 'react-query';

import { GroupList } from 'types/data';

const useInfiniteScroll = (
  target: React.RefObject<HTMLDivElement>,
  isFetching: boolean,
  data: GroupList | undefined,
  refetch: (
    options?: (RefetchOptions & RefetchQueryFilters<GroupList>) | undefined,
  ) => Promise<QueryObserverResult<GroupList, unknown>>,
  groups: GroupList['groups'],
) => {
  useEffect(() => {
    let observer: IntersectionObserver;

    const onIntersection = async (
      [entry]: IntersectionObserverEntry[],
      observer: IntersectionObserver,
    ) => {
      if (!entry.isIntersecting || isFetching || !data?.hasNextPage) return;

      refetch().then(() => {
        if (!data || !target.current) return;
        if (!data.hasNextPage || groups.length > 0) {
          observer.unobserve(target.current);
          return;
        }
      });
    };

    if (target) {
      observer = new IntersectionObserver(onIntersection, {
        threshold: 0.5,
      });

      if (!target.current) return;
      observer.observe(target.current);
    }

    return () => observer && observer.disconnect();
  }, [target, isFetching, refetch, groups.length, data]);
};

export default useInfiniteScroll;
