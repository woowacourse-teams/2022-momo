interface QueryParams {
  [key: string]: unknown;
}

const makeUrl = (baseUrl: string, queryParams: QueryParams) => {
  const queries = Object.keys(queryParams)
    .map(queryKey => {
      const value = queryParams[queryKey];

      return value === false || value === '' || Number(value) < 0
        ? ''
        : `${queryKey}=${value}`;
    })
    .filter(query => query !== '')
    .join('&');

  return [baseUrl, queries].join('?');
};

export { makeUrl };
