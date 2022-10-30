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
          imageUrl: 'https://image.moyeora.site/category/icon_study.svg',
        },
        {
          id: 2,
          name: '모각코',
          imageUrl: 'https://image.moyeora.site/category/icon_moco.svg',
        },
        {
          id: 3,
          name: '식사',
          imageUrl: 'https://image.moyeora.site/category/icon_eat.svg',
        },
        {
          id: 4,
          name: '카페',
          imageUrl: 'https://image.moyeora.site/category/icon_cafe.svg',
        },
        {
          id: 5,
          name: '술',
          imageUrl: 'https://image.moyeora.site/category/icon_drink.svg',
        },
        {
          id: 6,
          name: '운동',
          imageUrl: 'https://image.moyeora.site/category/icon_health.svg',
        },
        {
          id: 7,
          name: '게임',
          imageUrl: 'https://image.moyeora.site/category/icon_game.svg',
        },
        {
          id: 8,
          name: '여행',
          imageUrl: 'https://image.moyeora.site/category/icon_travel.svg',
        },
        {
          id: 9,
          name: '문화생활',
          imageUrl: 'https://image.moyeora.site/category/icon_culture.svg',
        },
        {
          id: 10,
          name: '기타',
          imageUrl: 'https://image.moyeora.site/category/icon_etc.svg',
        },
      ]),
    );
  }),
];
