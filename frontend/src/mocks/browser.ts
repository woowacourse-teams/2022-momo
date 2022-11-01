import { setupWorker } from 'msw';

import {
  groupHandler,
  categoryHandler,
  userHandler,
  authHandler,
} from './handlers';

const worker = setupWorker(
  ...categoryHandler,
  ...groupHandler,
  ...userHandler,
  ...authHandler,
);

export { worker };
