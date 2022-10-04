import { setupWorker } from 'msw';

import { groupHandler, categoryHandler } from './handlers';

const worker = setupWorker(...groupHandler, ...categoryHandler);

export { worker };
