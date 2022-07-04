import React from 'react';

import ReactDOM from 'react-dom/client';

import App from './App';

if (process.env.NODE_ENV === 'development') {
  const { worker } = require('mocks/browser');
  worker.start();
}

const root = ReactDOM.createRoot(document.getElementById('root')!);

root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
);
