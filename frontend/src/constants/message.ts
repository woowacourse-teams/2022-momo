import { GROUP_RULE } from './rule';

const GUIDE_MESSAGE = {
  AUTH: {
    NEED_LOGIN: '로그인이 필요한 서비스예요. 로그인을 먼저 해주세요 🙏',
    LOGIN_SUCCESS: '로그인에 성공했어요.',
    SIGNUP_SUCCESS: '회원가입에 성공했어요.',
    CONFIRM_LOGOUT: '로그아웃 하실건가요?',
    LOGOUT_SUCCESS: '로그아웃에 성공했어요.',
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
  GROUP: {
    CONFIRM_CLOSE_REQUEST: '모임 모집을 마감하시겠어요?',
    SUCCESS_CLOSE_REQUEST: '모임 모집을 마감했어요.',
    SUCCESS_JOIN_REQUEST: '참여에 성공했어요.',
    CONFIRM_EXIT_REQUEST: '참여를 취소할까요?',
    SUCCESS_EXIT_REQUEST: '참여 취소에 성공했어요.',
    SUCCESS_EDIT_REQUEST: '모임 정보 수정에 성공했어요.',
  },
};

const ERROR_MESSAGE = {
  SIGNUP: {
    INVALID_ID: '잘못된 형식의 아이디입니다.',
    INVALID_NICKNAME: '올바르지 않은 닉네임입니다.',
    INVALID_PASSWORD: '잘못된 형식의 비밀번호입니다.',
    INVALID_CONFIRMPASSWORD: '비밀번호 확인이 맞지 않습니다.',
    DUPLICATED_ID: '중복된 아이디입니다.',
  },
  AUTH: {
    NOT_EXIST_ID: '존재하지 않는 아이디입니다.',
    INCORRECT_PASSWORD: '비밀번호가 일치하지 않습니다.',
    FAILURE_LOGIN_REQUEST: '로그인에 실패했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
    FAILURE_SIGNUP_REQUEST:
      '회원가입에 실패했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
    FAILURE_LOGOUT_REQUEST:
      '로그아웃에 실패했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
  },
  CREATE: {
    NAME: `이름의 글자 수는 ${GROUP_RULE.NAME.MIN_LENGTH}자에서 ${GROUP_RULE.NAME.MAX_LENGTH}자 사이여야 해요.`,
    CAPACITY: `최대 인원은 ${GROUP_RULE.CAPACITY.MIN}명 이상 ${GROUP_RULE.CAPACITY.MAX}명 이하여야 해요.`,
    CATEGORY: '카테고리는 꼭 선택해 주세요.',
    DURATION: '날짜가 잘못 입력되었어요.',
    SCHEDULE_TIME: '시작 시간은 종료 시간 이전이어야 해요.',
    SCHEDULE_DAY: '잘못된 날짜예요. 다시 선택해주세요 😤',
    DEADLINE: '마감 날짜가 잘못 입력되었어요.',
    LOCATION: `장소는 ${GROUP_RULE.LOCATION.MAX_LENGTH}자 이내여야 해요.`,
    DESCRIPTION: `설명은 ${GROUP_RULE.DESCRIPTION.MAX_LENGTH}자 이내여야 해요.`,
    FAILURE_REQUEST:
      '모임을 생성하는 중 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
  },
  DELETE: {
    FAILURE_REQUEST:
      '모임을 삭제하는 중 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
  },
  MEMBER: {
    FAILURE_CONFIRM_PASSWORD: '비밀번호 확인이 일치하지 않아요.',
    FAILURE_REQUEST:
      '유저 정보를 불러오는 중 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
    FAILURE_CONFIRM_PASSWORD_REQUEST:
      '비밀번호를 확인하는 중 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
    FAILURE_NAME_REQUEST:
      '닉네임을 변경하는 중 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
    FAILURE_PASSWORD_REQUEST:
      '비밀번호를 변경하는 중 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
    FAILURE_WITHDRAWAL_REQUEST:
      '회원 탈퇴 중 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
  },
  GROUP: {
    FAILURE_CLOSE_GROUP:
      '모임 모집을 마감하는 중 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
    FAILURE_JOIN_GROUP:
      '모임에 참여하는 중 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
    FAILURE_EXIT_GROUP:
      '모임 참여를 취소하는 중 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
    FAILURE_EDIT_GROUP:
      '모임 정보를 수정하는 중 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
    FAILURE_LIKE_GROUP:
      '모임을 찜하던 중 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
  },
};

export { GUIDE_MESSAGE, ERROR_MESSAGE };
