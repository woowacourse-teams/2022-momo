import { rest } from 'msw';

const handlers = [
  rest.get('/api/login', (req, res, ctx) => {
    return res(ctx.status(200));
  }),
];

export { handlers };
