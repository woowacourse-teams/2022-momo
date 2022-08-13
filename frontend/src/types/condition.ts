export type ModalStateType = 'off' | 'login' | 'signup';

export interface SnackbarState {
  isShowing: boolean;
  message: string;
}
