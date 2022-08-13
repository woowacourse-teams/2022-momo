import { createPortal } from 'react-dom';

interface PortalProps {
  to: 'modal' | 'snackbar';
  children: React.ReactNode;
}

function Portal({ to, children }: PortalProps) {
  const modalElement = document.getElementById('modal') as HTMLElement;
  const snackbarElement = document.getElementById('snackbar') as HTMLElement;

  switch (to) {
    case 'modal':
      return createPortal(children, modalElement);
    case 'snackbar':
      return createPortal(children, snackbarElement);
  }
}

export default Portal;
