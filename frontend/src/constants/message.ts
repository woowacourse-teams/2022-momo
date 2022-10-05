import { GROUP_RULE, MEMBER_RULE } from './rule';

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
    WITHDRAWAL_MEMBER: '탈퇴한 회원이에요.',
  },
  GROUP: {
    CONFIRM_CLOSE_REQUEST: '모임 모집을 마감하시겠어요?',
    SUCCESS_CLOSE_REQUEST: '모임 모집을 마감했어요.',
    SUCCESS_JOIN_REQUEST: '참여에 성공했어요.',
    CONFIRM_EXIT_REQUEST: '참여를 취소할까요?',
    SUCCESS_EXIT_REQUEST: '참여 취소에 성공했어요.',
    SUCCESS_EDIT_REQUEST: '모임 정보 수정에 성공했어요.',
    SUCCESS_LIKE_GROUP: '찜한 모임에 추가했어요.',
    SUCCESS_UNLIKE_GROUP: '찜한 목록에서 삭제했어요.',
  },
};

const CLIENT_ERROR_MESSAGE = {
  SIGNUP: {
    INVALID_ID: `아이디는 ${MEMBER_RULE.ID.MIN_LENGTH}자에서 ${MEMBER_RULE.ID.MAX_LENGTH}자 사이여야 해요.`,
    INVALID_NICKNAME: '올바르지 않은 닉네임이에요.',
    INVALID_PASSWORD: '잘못된 형식의 비밀번호에요.',
    INVALID_CONFIRMPASSWORD: '비밀번호 확인이 맞지 않아요.',
  },
  AUTH: {
    NOT_EXIST_ID: '존재하지 않는 아이디에요.',
    INCORRECT_PASSWORD: '비밀번호가 일치하지 않아요.',
    FAILURE_LOGIN_REQUEST: '로그인에 실패했어요. ',
    FAILURE_SIGNUP_REQUEST: '회원가입에 실패했어요. ',
    FAILURE_LOGOUT_REQUEST: '로그아웃에 실패했어요. ',
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
    FAILURE_REQUEST: '모임을 생성하는 중 에러가 발생했어요. ',
  },
  DELETE: {
    FAILURE_REQUEST: '모임을 삭제하는 중 에러가 발생했어요. ',
  },
  MEMBER: {
    FAILURE_CONFIRM_PASSWORD: '비밀번호 확인이 일치하지 않아요.',
    FAILURE_REQUEST: '유저 정보를 불러오는 중 에러가 발생했어요. ',
    FAILURE_CONFIRM_PASSWORD_REQUEST:
      '비밀번호를 확인하는 중 에러가 발생했어요. ',
    FAILURE_NAME_REQUEST: '닉네임을 변경하는 중 에러가 발생했어요. ',
    FAILURE_PASSWORD_REQUEST: '비밀번호를 변경하는 중 에러가 발생했어요. ',
    FAILURE_WITHDRAWAL_REQUEST: '회원 탈퇴 중 에러가 발생했어요. ',
  },
  GROUP: {
    FAILURE_CLOSE_GROUP: '모임 모집을 마감하는 중 에러가 발생했어요. ',
    FAILURE_JOIN_GROUP: '모임에 참여하는 중 에러가 발생했어요. ',
    FAILURE_EXIT_GROUP: '모임 참여를 취소하는 중 에러가 발생했어요. ',
    FAILURE_EDIT_GROUP: '모임 정보를 수정하는 중 에러가 발생했어요. ',
    FAILURE_LIKE_GROUP: '모임을 찜하던 중 에러가 발생했어요. ',
  },
  UNHANDLED:
    '알 수 없는 클라이언트 에러가 발생했어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
};

const SERVER_ERROR_MESSAGE = {
  SIGNUP: {
    SIGNUP_001: '잘못된 형식의 아이디에요.',
    SIGNUP_002: '잘못된 형식의 비밀번호에요.',
    SIGNUP_003: '중복된 아이디에요.',
  },
  AUTH: {
    AUTH_001: '토큰의 유효기간이 만료되었어요. 새로운 토큰을 요청할게요.',
    AUTH_002: '유효하지 않은 토큰이에요. 다시 로그인해주세요.',
    AUTH_003: '로그인이 필요해요.',
    AUTH_004: '모임의 수정과 삭제는 주최자만 가능해요.',
  },
  OAUTH: {
    OAUTH_001:
      '구글 서버와 통신 중 오류가 발생했어요. 잠시 후에 다시 시도해주세요.',
  },
  LOGIN: {
    LOGIN_001: '아이디 또는 비밀번호가 잘못되었어요.',
  },
  MEMBER: {
    MEMBER_001: '존재하지 않는 회원이에요.',
    MEMBER_002: '탈퇴한 회원이에요.',
    MEMBER_003: '현재 진행중인 모임이 있어 탈퇴할 수 없어요.',
    MEMBER_004: '비밀번호가 일치하지 않아요.',
    MEMBER_005: '이름이 비어있어요.',
    MEMBER_006: `이름이 ${MEMBER_RULE.NAME.MAX_LENGTH}자를 넘어요.`,
    MEMBER_007: '비밀번호가 비어있어요.',
    MEMBER_008: '비밀번호가 형식에 맞지 않아요.',
    MEMBER_010: '아이디가 비어있어요.',
    MEMBER_011: '구글 아이디가 이메일 형식에 맞지 않아요.',
    MEMBER_012: '아이디나 비밀번호가 맞지 않아요.',
    MEMBER_013: `아이디는 ${MEMBER_RULE.ID.MIN_LENGTH}자에서 ${MEMBER_RULE.ID.MAX_LENGTH}자 사이여야 해요.`,
  },
  GROUP: {
    GROUP_001: '존재하지 않는 모임이에요.',
    GROUP_002: '마감시간은 지금 시점 이후로 설정해주세요.',
    GROUP_003: '모임 진행 시작 날짜는 종료일자 이전이어야 해요.',
    GROUP_004: '시작 날짜와 종료 날짜는 과거일 수 없어요.',
    GROUP_005: '마감 날짜는 시작 날짜 이전으로 설정해주세요.',
    GROUP_006: '일정의 시작 시간은 종료 시간 이전이어야 해요.',
    GROUP_007: '일정은 모임 기간에만 등록할 수 있어요.',
    GROUP_008: '모임의 이름은 빈 값이 될 수 없어요.',
    GROUP_009: `모집 인원은 ${GROUP_RULE.CAPACITY.MIN}명 이상 ${GROUP_RULE.CAPACITY.MAX}명 이하여야 해요.`,
    GROUP_010: '모집 인원은 현재 참가자 수보다 적을 수 없어요.',
    GROUP_011: '모임이 조기 마감되었어요.',
    GROUP_012: '모임이 마감되었어요.',
    GROUP_013: '모집 인원이 가득 찼어요.',
    GROUP_014: '참여자가 존재하는 모임이에요.',
    GROUP_015: '모임의 주최자는 참여 상태를 변경할 수 없어요.',
    GROUP_016: '이미 참여중인 모임이에요.',
    GROUP_017: '모임의 주최자만 접근할 수 있어요.',
    GROUP_018: '해당 모임에 참여하지 않았어요.',
    GROUP_019: '이미 찜한 모임이에요.',
    GROUP_020: '찜하지 않은 모임이에요.',
    GROUP_021: `이름의 글자 수는 ${GROUP_RULE.NAME.MIN_LENGTH}자에서 ${GROUP_RULE.NAME.MAX_LENGTH}자 사이여야 해요.`,
    GROUP_022: `모임의 설명은 ${GROUP_RULE.DESCRIPTION.MAX_LENGTH}자를 넘을 수 없어요.`,
  },
  CATEGORY: {
    CATEGORY_001: '존재하지 않는 카테고리에요.',
  },
  SERVER: {
    NOT_FOUND: '존재하지 않는 주소에요. 주소를 다시 확인해주세요.',
    VALIDATION_001: '잘못된 요청이에요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
    UNHANDLED:
      '알 수 없는 서버 에러가 발생하였어요. 관리자에게 문의해주세요 🙇‍♂️🙇‍♀️',
  },
};

export { GUIDE_MESSAGE, CLIENT_ERROR_MESSAGE, SERVER_ERROR_MESSAGE };
