import { rest } from 'msw';

import { baseURL } from 'apis/axios';
import { API_PATH } from 'constants/path';

const apiURL = `${baseURL}${API_PATH.GROUP}`;

const groupDetails = [
  {
    name: '오늘 끝나고 맥주 드실 분',
    host: {
      id: 1,
      name: '4기 이프',
    },
    categoryId: 5,
    duration: {
      start: '2022-07-18',
      end: '2022-07-18',
    },
    schedules: [
      {
        id: 1,
        date: '2022-07-18',
        startTime: '18:00',
        endTime: '20:00',
      },
    ],
    deadline: '2022-07-18T17:59:00',
    location: '둘둘치킨 선릉점',
    description: `오늘로 레벨 3의 첫 데모 데이가 끝납니다.\n저녁식사 하면서 간단하게 한 잔 하려고 하는데 오실 분들은 자유롭게 참여해주세요.\n메뉴는 치킨에 맥주입니다.\n술 강요 없음 / 딱 한 잔 가능 / 주종 선택 자유`,
  },
  {
    name: '주말에 같이 야구 봐요',
    host: {
      id: 2,
      name: '4기 유세지',
    },
    categoryId: 9,
    duration: {
      start: '2022-07-20',
      end: '2022-07-20',
    },
    schedules: [
      {
        date: '2022-07-20',
        startTime: '13:00',
        endTime: '18:00',
      },
    ],
    deadline: '2022-07-19T23:59:00',
    location: '잠실종합운동장',
    description: `주말에 같이 야구 보실 분 구합니다.\n외야 쪽 좌석으로 잡을 예정이고 두산이나 엘지 팬이시면 좋겠습니다.\n편하게 연락주세여 ⚾`,
  },
  {
    name: 'Git 브랜칭 전략 스터디 모집',
    host: {
      id: 3,
      name: '4기 렉스',
    },
    categoryId: 1,
    duration: {
      start: '2022-07-18',
      end: '2022-08-18',
    },
    schedules: [
      {
        date: '2022-08-18',
        startTime: '20:00',
        endTime: '21:00',
      },
    ],
    deadline: '2022-07-19T17:59:00',
    location: '선릉 테크살롱',
    description: `Git 브랜칭 전략에 관심이 생겨서 한 달간 함께 스터디 하실 분을 모집합니다.\n기본적인 Git 사용법을 숙지하신 분들을 대상으로 진행하니 어느정도 다루시는 분들이 오시면 좋습니다.\n\n매주 토요일 저녁에 진행하고, 시간은 앞뒤로 조정 가능합니다.\n많은 관심 바랍니다.`,
  },
  {
    name: '탑건: 매버릭 같이 보실 분 계신가요',
    host: {
      id: 4,
      name: '4기 유콩',
    },
    categoryId: 9,
    duration: {
      start: '2022-07-24',
      end: '2022-07-24',
    },
    schedules: [
      {
        date: '2022-07-24',
        start: '20:00',
        end: '21:00',
      },
    ],
    deadline: '2022-07-23T23:59:00',
    location: 'CGV 용산아이파크몰',
    description: `안녕하세요! 목요일에 같이 영화보러가요 🎞🎞`,
  },
];

const groupSummaries = groupDetails.map((groupDetail, index) => ({
  id: index + 1,
  name: groupDetail.name,
  host: groupDetail.host,
  categoryId: groupDetail.categoryId,
  deadline: groupDetail.deadline,
}));

const groupHandler = [
  // 모임 생성
  rest.post(apiURL, (req, res, ctx) => {
    if (!req.body) return res(ctx.status(400));

    return res(ctx.status(201), ctx.json({ groupId: 1 }));
  }),

  // 모임 목록 조회
  rest.get(apiURL, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(groupSummaries));
  }),

  // 모임 단일 조회
  rest.get(`${apiURL}/:groupId`, (req, res, ctx) => {
    const { groupId } = req.params;

    if (!groupId) return res(ctx.status(400));

    return res(ctx.status(200), ctx.json(groupDetails[Number(groupId) - 1]));
  }),

  // 모임 수정
  rest.put(`${apiURL}/:groupId`, (req, res, ctx) => {
    const { groupId } = req.params;

    if (!groupId) return res(ctx.status(400));

    return res(ctx.status(200));
  }),

  // 모임 삭제
  rest.delete(`${apiURL}/:groupId`, (req, res, ctx) => {
    const { groupId } = req.params;

    if (!groupId) return res(ctx.status(400));

    return res(ctx.status(204));
  }),
];

export { groupHandler };
