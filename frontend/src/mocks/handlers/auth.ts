import { rest } from 'msw';

import { baseURL } from 'apis/axios';
import { checkValidId, checkValidPassword } from 'components/Signup/validate';
import { API_PATH } from 'constants/path';

export const authHandler = [
  // 회원가입
  rest.post(`${baseURL}${API_PATH.MEMBER.BASE}`, (req, res, ctx) => {
    if (req.body && typeof req.body === 'object') {
      // 잘못된 형식의 아이디인 경우
      if (!checkValidId(req.body.userId)) {
        return res(
          ctx.status(400),
          ctx.json({
            message: 'SIGNUP_001',
          }),
        );
      }

      // 잘못된 형식의 비밀번호인 경우
      if (!checkValidPassword(req.body.password)) {
        return res(
          ctx.status(400),
          ctx.json({
            message: 'SIGNUP_002',
          }),
        );
      }
    }

    return res(ctx.status(201));
  }),

  // 로그인
  rest.post(`${baseURL}${API_PATH.AUTH.LOGIN}`, (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        accessToken:
          'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1IiwiaWF0IjoxNjY2MjY4MDYzLCJleHAiOjE2NjYyNjk4NjN9.TSgh-LJ4CksrGXV9j6NVQJ7eV2K7XvYnD2HidN_4c-8',
        refreshToken:
          'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1IiwiaWF0IjoxNjY2MjY4MDYzLCJleHAiOjE2NjY4NzI4NjN9.dND9ASsGZDAC8IFLSoQ97gtNF_yMyP82s62pua7w228',
      }),
    );
  }),

  // OAuth 구글 링크 & 로그인
  rest.get(`${baseURL}${API_PATH.AUTH.GOOGLE_LOGIN}`, (req, res, ctx) => {
    if (req.url.searchParams.get('code')) {
      return res(
        ctx.status(200),
        ctx.json({
          accessToken:
            'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjU5OTI3MTMxLCJleHAiOjE2NTk5MzA3MzF9.VG8BIv3X1peT0e16OdSqq4EkkgDd1bHbYX99oglxkS4',
          refreshToken:
            'eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NjAwMjk0OTUsImV4cCI6MTY2MDAzMzA5NX0.qwxal9Fp78G5l6RWbG9SMvOVnb0pnrEkWPHMPBmQw8c',
        }),
      );
    }

    return res(
      ctx.status(200),
      ctx.json({
        oauthLink:
          'https://accounts.google.com/o/oauth2/auth?redirect_uri=http://www.moyeora.com/auth/google&scope=openid+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile&response_type=code&client_id=clientId',
      }),
    );
  }),

  // 로그아웃
  rest.post(`${baseURL}${API_PATH.AUTH.LOGOUT}`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  // 액세스 토큰 재발급
  rest.post(
    `${baseURL}${API_PATH.AUTH.REFRESH_ACCESS_TOKEN}`,
    (req, res, ctx) => {
      return res(
        ctx.status(200),
        ctx.json({
          accessToken:
            'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1IiwiaWF0IjoxNjY2MjY4MDYzLCJleHAiOjE2NjYyNjk4NjN9.TSgh-LJ4CksrGXV9j6NVQJ7eV2K7XvYnD2HidN_4c-8',
        }),
      );
    },
  ),
];
