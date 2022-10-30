import { rest } from 'msw';

import { baseURL } from 'apis/axios';
import { API_PATH } from 'constants/path';

const apiURL = `${baseURL}${API_PATH.MEMBER}`;

export const memberHandler = [
  // 사용자 정보 조회
  rest.get(apiURL, (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        id: 1,
        userId: 'lah1203',
        name: '하리',
      }),
    );
  }),

  // 회원 탈퇴
  rest.delete(apiURL, (req, res, ctx) => {
    return res(ctx.status(204));
  }),

  // 닉네임 수정
  rest.patch(`${apiURL}/name`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  // 비밀번호 수정
  rest.patch(`${apiURL}/password`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),
];
