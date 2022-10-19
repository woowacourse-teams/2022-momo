export type ModalStateType =
  | 'off'
  | 'login'
  | 'signup'
  | 'confirmPassword'
  | 'postcode'
  | 'thumbnail';

export interface SnackbarState {
  type: 'basic' | 'error';
  isShowing: boolean;
  message: string;
}
