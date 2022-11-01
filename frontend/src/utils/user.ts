const getLoginType = (userId: string): 'basic' | 'oauth' => {
  if (userId.endsWith('@gmail.com')) {
    return 'oauth';
  }

  return 'basic';
};

export { getLoginType };
