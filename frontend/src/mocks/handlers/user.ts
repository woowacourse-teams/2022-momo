import { rest } from 'msw';

import { baseURL } from 'apis/axios';
import { checkValidPassword } from 'components/Signup/validate';
import { API_PATH } from 'constants/path';
import { MEMBER_RULE } from 'constants/rule';

const user = {
  id: 1,
  userId: 'lah1203',
  password: 'momo123!',
  name: '하리',
};

export const userHandler = [
  // 사용자 정보 조회
  rest.get(`${baseURL}${API_PATH.MEMBER.BASE}`, (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({ id: user.id, name: user.name, userId: user.userId }),
    );
  }),

  // 회원 탈퇴
  rest.delete(`${baseURL}${API_PATH.MEMBER.BASE}`, (req, res, ctx) => {
    return res(ctx.status(204));
  }),

  // 닉네임 수정
  rest.patch(`${baseURL}${API_PATH.MEMBER.NAME}`, (req, res, ctx) => {
    if (req.body && typeof req.body === 'object') {
      // 닉네임이 빈 값인 경우
      if (req.body.name === '') {
        return res(
          ctx.status(400),
          ctx.json({
            message: 'MEMBER_005',
          }),
        );
      }

      // 닉네임이 최대 글자 수를 넘는 경우
      if (req.body.name.length > MEMBER_RULE.NAME.MAX_LENGTH) {
        return res(
          ctx.status(400),
          ctx.json({
            message: 'MEMBER_006',
          }),
        );
      }

      user.name = req.body.name;
    }

    return res(ctx.status(200));
  }),

  // 비밀번호 수정
  rest.patch(`${baseURL}${API_PATH.MEMBER.PASSWORD}`, (req, res, ctx) => {
    if (req.body && typeof req.body === 'object') {
      // 입력한 이전 비밀번호가 일치하지 않는 경우
      if (req.body.oldPassword !== user.password) {
        return res(
          ctx.status(400),
          ctx.json({
            message: 'MEMBER_004',
          }),
        );
      }

      // 형식에 맞지 않는 비밀번호인 경우
      if (!checkValidPassword(req.body.newPassword)) {
        return res(
          ctx.status(400),
          ctx.json({
            message: 'MEMBER_008',
          }),
        );
      }

      user.password = req.body.newPassword;
    }

    return res(ctx.status(200));
  }),
];
