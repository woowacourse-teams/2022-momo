const GROUP_RULE = {
  NAME: {
    MIN_LENGTH: 1,
    MAX_LENGTH: 50,
  },
  CAPACITY: {
    MIN: 1,
    MAX: 99,
  },
  LOCATION: {
    MAX_LENGTH: 50,
  },
  DESCRIPTION: {
    MAX_LENGTH: 1000,
  },
};

const MEMBER_RULE = {
  NAME: {
    MIN_LENGTH: 2,
    MAX_LENGTH: 30,
  },
};

export { GROUP_RULE, MEMBER_RULE };
