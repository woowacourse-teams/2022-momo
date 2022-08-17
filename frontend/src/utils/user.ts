const getLoginType = (userId: string) => {
  if (userId.endsWith('@gmail.com')) {
    return 'oauth';
  }

  return 'basic';
};

export { getLoginType };
