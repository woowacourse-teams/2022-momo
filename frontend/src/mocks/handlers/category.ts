import { rest } from 'msw';

import { baseURL } from 'apis/axios';
import { API_PATH } from 'constants/path';

const apiURL = `${baseURL}${API_PATH.CATEGORY}`;

export const categoryHandler = [
  // 카테고리 정보 조회
  rest.get(apiURL, (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json([
        {
          id: 1,
          name: '스터디',
        },
        {
          id: 2,
          name: '모각코',
        },
        {
          id: 3,
          name: '식사',
        },
        {
          id: 4,
          name: '카페',
        },
        {
          id: 5,
          name: '술',
        },
        {
          id: 6,
          name: '운동',
        },
        {
          id: 7,
          name: '게임',
        },
        {
          id: 8,
          name: '여행',
        },
        {
          id: 9,
          name: '문화생활',
        },
        {
          id: 10,
          name: '기타',
        },
      ]),
    );
  }),
];
