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
  ID: {
    MIN_LENGTH: 4,
    MAX_LENGTH: 50,
  },
  NAME: {
    MIN_LENGTH: 1,
    MAX_LENGTH: 20,
  },
  PASSWORD: {
    MIN_LENGTH: 8,
    MAX_LENGTH: 16,
  },
};

const SNACKBAR_ANIMATION_TIME = 500;

export { GROUP_RULE, MEMBER_RULE, SNACKBAR_ANIMATION_TIME };
