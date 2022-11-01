import { rest } from 'msw';

import { baseURL } from 'apis/axios';
import { API_PATH } from 'constants/path';

const apiURL = `${baseURL}${API_PATH.GROUP}`;

const groupHandler = [
  // 모임 생성
  rest.post(apiURL, (req, res, ctx) => {
    if (!req.body) return res(ctx.status(400));

    return res(ctx.status(201), ctx.json({ groupId: 1 }));
  }),

  // 모임 목록 조회
  rest.get(apiURL, (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        hasNextPage: false,
        pageNumber: 0,
        groups: [
          {
            id: 37,
            name: '테코톡 스터디',
            host: { id: 68, name: '팰린드롬' },
            categoryId: 1,
            capacity: 99,
            numOfParticipant: 4,
            finished: false,
            deadline: '2022-11-01T23:59',
            like: false,
            imageUrl:
              'https://image.moyeora.site/group/default/thumbnail_study.jpg',
          },
          {
            id: 31,
            name: '소환사의협곡에 오신것을 환영합니다',
            host: { id: 60, name: '한글도가입되나보다' },
            categoryId: 7,
            capacity: 33,
            numOfParticipant: 3,
            finished: false,
            deadline: '2022-12-31T23:59',
            like: false,
            imageUrl:
              'https://image.moyeora.site/group/default/thumbnail_game.jpg',
          },
          {
            id: 30,
            name: '마셔라부어라',
            host: { id: 60, name: '한글도가입되나보다' },
            categoryId: 5,
            capacity: 99,
            numOfParticipant: 2,
            finished: false,
            deadline: '2022-11-10T13:00',
            like: false,
            imageUrl:
              'https://image.moyeora.site/group/default/thumbnail_drink.jpg',
          },
        ],
      }),
    );
  }),

  // 모임 단일 조회
  rest.get(`${apiURL}/:id`, (req, res, ctx) => {
    const { id } = req.params;

    if (!id) return res(ctx.status(400));

    return res(
      ctx.status(200),
      ctx.json({
        name: '테코톡 스터디',
        host: { id: 68, name: '팰린드롬' },
        categoryId: 1,
        capacity: 99,
        duration: { start: '2022-11-02', end: '2022-11-30' },
        schedules: [
          { date: '2022-11-05', startTime: '21:00:00', endTime: '22:00:00' },
          { date: '2022-11-12', startTime: '21:00:00', endTime: '22:00:00' },
          { date: '2022-11-19', startTime: '21:00:00', endTime: '22:00:00' },
          { date: '2022-11-26', startTime: '21:00:00', endTime: '22:00:00' },
        ],
        finished: false,
        deadline: '2022-11-01T23:59',
        location: {
          address: '서울 관악구 낙성대역3길 3',
          buildingName: '',
          detail: '서울역',
        },
        like: false,
        description:
          '우테코 5기 합격을 위해서 테코톡 스터디 진행합니다\n관심 있으신 분은 참여 ㄱㄱ',
        imageUrl:
          'https://image.moyeora.site/group/default/thumbnail_study.jpg',
      }),
    );
  }),

  // 모임 참여자 조회
  rest.get(`${apiURL}/:id/participants`, (req, res, ctx) => {
    const { id } = req.params;

    if (!id) return res(ctx.status(400));

    return res(
      ctx.status(200),
      ctx.json([
        { id: 68, name: '팰린드롬' },
        { id: 2, name: '유세지' },
        { id: 74, name: '?!@?#!?@#?~?@#?!?@#?' },
        { id: 80, name: 'moyeora' },
      ]),
    );
  }),

  // 참여한 모임 조회
  rest.get(`${apiURL}/me/participated`, (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        hasNextPage: false,
        pageNumber: 0,
        groups: [
          {
            id: 37,
            name: '테코톡 스터디',
            host: { id: 68, name: '팰린드롬' },
            categoryId: 1,
            capacity: 99,
            numOfParticipant: 4,
            finished: false,
            deadline: '2022-11-01T23:59',
            like: true,
            imageUrl:
              'https://image.moyeora.site/group/default/thumbnail_study.jpg',
          },
          {
            id: 31,
            name: '소환사의협곡에 오신것을 환영합니다',
            host: { id: 60, name: '한글도가입되나보다' },
            categoryId: 7,
            capacity: 33,
            numOfParticipant: 3,
            finished: false,
            deadline: '2022-12-31T23:59',
            like: true,
            imageUrl:
              'https://image.moyeora.site/group/default/thumbnail_game.jpg',
          },
          {
            id: 30,
            name: '마셔라부어라',
            host: { id: 60, name: '한글도가입되나보다' },
            categoryId: 5,
            capacity: 99,
            numOfParticipant: 2,
            finished: false,
            deadline: '2022-11-10T13:00',
            like: true,
            imageUrl:
              'https://image.moyeora.site/group/default/thumbnail_drink.jpg',
          },
        ],
      }),
    );
  }),

  // 주최한 모임 조회
  rest.get(`${apiURL}/me/hosted`, (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        hasNextPage: false,
        pageNumber: 0,
        groups: [
          {
            id: 37,
            name: '테코톡 스터디',
            host: { id: 68, name: '팰린드롬' },
            categoryId: 1,
            capacity: 99,
            numOfParticipant: 4,
            finished: false,
            deadline: '2022-11-01T23:59',
            like: true,
            imageUrl:
              'https://image.moyeora.site/group/default/thumbnail_study.jpg',
          },
          {
            id: 31,
            name: '소환사의협곡에 오신것을 환영합니다',
            host: { id: 60, name: '한글도가입되나보다' },
            categoryId: 7,
            capacity: 33,
            numOfParticipant: 3,
            finished: false,
            deadline: '2022-12-31T23:59',
            like: true,
            imageUrl:
              'https://image.moyeora.site/group/default/thumbnail_game.jpg',
          },
          {
            id: 30,
            name: '마셔라부어라',
            host: { id: 60, name: '한글도가입되나보다' },
            categoryId: 5,
            capacity: 99,
            numOfParticipant: 2,
            finished: false,
            deadline: '2022-11-10T13:00',
            like: true,
            imageUrl:
              'https://image.moyeora.site/group/default/thumbnail_drink.jpg',
          },
        ],
      }),
    );
  }),

  // 찜한 모임 조회
  rest.get(`${apiURL}/me/liked`, (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        hasNextPage: false,
        pageNumber: 0,
        groups: [
          {
            id: 37,
            name: '테코톡 스터디',
            host: { id: 68, name: '팰린드롬' },
            categoryId: 1,
            capacity: 99,
            numOfParticipant: 4,
            finished: false,
            deadline: '2022-11-01T23:59',
            like: true,
            imageUrl:
              'https://image.moyeora.site/group/default/thumbnail_study.jpg',
          },
          {
            id: 31,
            name: '소환사의협곡에 오신것을 환영합니다',
            host: { id: 60, name: '한글도가입되나보다' },
            categoryId: 7,
            capacity: 33,
            numOfParticipant: 3,
            finished: false,
            deadline: '2022-12-31T23:59',
            like: true,
            imageUrl:
              'https://image.moyeora.site/group/default/thumbnail_game.jpg',
          },
          {
            id: 30,
            name: '마셔라부어라',
            host: { id: 60, name: '한글도가입되나보다' },
            categoryId: 5,
            capacity: 99,
            numOfParticipant: 2,
            finished: false,
            deadline: '2022-11-10T13:00',
            like: true,
            imageUrl:
              'https://image.moyeora.site/group/default/thumbnail_drink.jpg',
          },
        ],
      }),
    );
  }),

  // 모임 수정
  rest.put(`${apiURL}/:id`, (req, res, ctx) => {
    const { id } = req.params;

    if (!id) return res(ctx.status(400));

    return res(ctx.status(200));
  }),

  // 모임 조기마감
  rest.post(`${apiURL}/:id/close`, (req, res, ctx) => {
    const { id } = req.params;

    if (!id) return res(ctx.status(400));

    return res(ctx.status(200));
  }),

  // 모임 삭제
  rest.delete(`${apiURL}/:id`, (req, res, ctx) => {
    const { id } = req.params;

    if (!id) return res(ctx.status(400));

    return res(ctx.status(204));
  }),

  // 모임 찜하기
  rest.post(`${apiURL}/:id/like`, (req, res, ctx) => {
    const { id } = req.params;

    if (!id) return res(ctx.status(400));

    return res(ctx.status(200));
  }),

  // 모임 찜 취소
  rest.delete(`${apiURL}/:id/like`, (req, res, ctx) => {
    const { id } = req.params;

    if (!id) return res(ctx.status(400));

    return res(ctx.status(200));
  }),

  // 모임 썸네일 변경
  rest.post(`${apiURL}/:id/thumbnail`, (req, res, ctx) => {
    const { id } = req.params;

    if (!id) return res(ctx.status(400));

    return res(ctx.status(201));
  }),

  // 모임 썸네일 초기화
  rest.delete(`${apiURL}/:id/thumbnail`, (req, res, ctx) => {
    const { id } = req.params;

    if (!id) return res(ctx.status(400));

    return res(ctx.status(204));
  }),
];

export { groupHandler };
