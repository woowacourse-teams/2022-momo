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

const groupHandler = [
  // 모임 생성
  rest.post(apiURL, (req, res, ctx) => {
    if (!req.body) return res(ctx.status(400));

    return res(ctx.status(201), ctx.json({ groupId: 1 }));
  }),

  // 모임 목록 조회
  rest.get(apiURL, (req, res, ctx) => {
    const pageNumber = req.url.searchParams.get('page');

    const pageResponse = {
      hasNextPage: true,
      pageNumber: Number(pageNumber),
      groups: [
        {
          id: 2,
          name: '슬퍼하지마 nonono',
          host: { id: 3, name: '유세지' },
          categoryId: 2,
          capacity: 1,
          numOfParticipant: 1,
          finished: true,
          deadline: '2022-08-04T22:16',
        },
        {
          id: 6,
          name: '설명이 필요없는 모임',
          host: { id: 3, name: '유세지' },
          categoryId: 5,
          capacity: 6,
          numOfParticipant: 4,
          finished: true,
          deadline: '2022-08-05T18:49',
        },
        {
          id: 7,
          name: '모모',
          host: { id: 2, name: '하리' },
          categoryId: 2,
          capacity: 99,
          numOfParticipant: 4,
          finished: false,
          deadline: '2022-08-12T23:59',
        },
        {
          id: 8,
          name: '방학엠티~!!',
          host: { id: 1, name: '이프' },
          categoryId: 8,
          capacity: 6,
          numOfParticipant: 6,
          finished: true,
          deadline: '2022-08-19T23:59',
        },
        {
          id: 10,
          name: '롯데월드 팟 모집',
          host: { id: 2, name: '하리' },
          categoryId: 9,
          capacity: 10,
          numOfParticipant: 3,
          finished: true,
          deadline: '2022-08-06T23:59',
        },
        {
          id: 11,
          name: '카러플 하실분',
          host: { id: 2, name: '하리' },
          categoryId: 7,
          capacity: 99,
          numOfParticipant: 3,
          finished: true,
          deadline: '2022-08-07T23:59',
        },
        {
          id: 12,
          name: '연식 가자',
          host: { id: 2, name: '하리' },
          categoryId: 3,
          capacity: 4,
          numOfParticipant: 2,
          finished: true,
          deadline: '2022-08-15T00:00',
        },
        {
          id: 14,
          name: '같이 CS 공부해요',
          host: { id: 4, name: '유콩' },
          categoryId: 1,
          capacity: 3,
          numOfParticipant: 1,
          finished: true,
          deadline: '2022-08-06T11:32',
        },
        {
          id: 15,
          name: '이번 주말 잠실팟',
          host: { id: 1, name: '이프' },
          categoryId: 2,
          capacity: 99,
          numOfParticipant: 2,
          finished: true,
          deadline: '2022-08-05T23:59',
        },
        {
          id: 16,
          name: '제 단독 콘서트 초대합니다',
          host: { id: 6, name: '아이유' },
          categoryId: 9,
          capacity: 99,
          numOfParticipant: 3,
          finished: false,
          deadline: '2022-08-14T23:59',
        },
        {
          id: 18,
          name: '축구 하실분',
          host: { id: 9, name: '리오넬메시' },
          categoryId: 6,
          capacity: 99,
          numOfParticipant: 1,
          finished: true,
          deadline: '2022-08-06T13:00',
        },
        {
          id: 19,
          name: '선릉~정릉~나들이',
          host: { id: 10, name: '봉봉' },
          categoryId: 8,
          capacity: 10,
          numOfParticipant: 2,
          finished: false,
          deadline: '2022-08-30T23:59',
        },
      ],
    };
    const lastPageResponse = { ...pageResponse, hasNextPage: false };

    if (Number(pageNumber) > 2)
      return res(ctx.status(200), ctx.json(lastPageResponse));

    return res(ctx.status(200), ctx.json(pageResponse));
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
