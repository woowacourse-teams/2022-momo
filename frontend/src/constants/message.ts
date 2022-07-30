import { GROUP_RULE } from './rule';

const GUIDE_MESSAGE = {
  AUTH: {
    LOGIN_SUCCESS: '로그인에 성공했어요.',
    SIGNUP_SUCCESS: '회원가입에 성공했어요.',
  },
  DELETE: {
    CONFIRM_REQUEST: '정말로 삭제하실 건가요? 이 작업은 돌이킬 수 없어요 🥺',
    SUCCESS_REQUEST: '모임을 성공적으로 삭제했어요.',
  },
  MEMBER: {
    SUCCESS_NAME_REQUEST: '닉네임 변경에 성공했어요.',
    SUCCESS_PASSWORD_REQUEST: '비밀번호 변경에 성공했어요.',
    CONFIRM_WITHDRAWAL_REQUEST:
      '정말로 탈퇴하실 건가요? 이 작업은 돌이킬 수 없어요 🥺',
    SUCCESS_WITHDRAWAL_REQUEST: '회원 탈퇴에 성공했어요. 다음에 다시 만나요 😊',
  },
};

const ERROR_MESSAGE = {
  AUTH: {
    FAILURE_LOGIN_REQUEST: '로그인에 실패했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
    FAILURE_SIGNUP_REQUEST:
      '회원가입에 실패했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
  },
  CREATE: {
    NAME: `이름의 글자 수는 ${GROUP_RULE.NAME.MIN_LENGTH}자에서 ${GROUP_RULE.NAME.MAX_LENGTH}자 사이여야 해요.`,
    CAPACITY: '',
    CATEGORY: '카테고리는 꼭 선택해 주세요.',
    DURATION: '날짜가 잘못 입력되었어요.',
    DEADLINE: '마감 날짜가 잘못 입력되었어요.',
    LOCATION: `장소는 ${GROUP_RULE.LOCATION.MAX_LENGTH}자 이내여야 해요.`,
    DESCRIPTION: `설명은 ${GROUP_RULE.DESCRIPTION.MAX_LENGTH}자 이내여야 해요.`,
    FAILURE_REQUEST:
      '모임을 생성하는데 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
  },
  DELETE: {
    FAILURE_REQUEST:
      '모임을 삭제하는데 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
  },
  MEMBER: {
    FAILURE_CONFIRM_PASSWORD: '비밀번호 확인이 일치하지 않아요.',
    FAILURE_REQUEST:
      '유저 정보를 불러오는데 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
    FAILURE_NAME_REQUEST:
      '닉네임을 변경하는데 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
    FAILURE_PASSWORD_REQUEST:
      '비밀번호를 변경하는데 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
    FAILURE_WITHDRAWAL_REQUEST:
      '회원 탈퇴에 문제가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
  },
};

export { GUIDE_MESSAGE, ERROR_MESSAGE };
