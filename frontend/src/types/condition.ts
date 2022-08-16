export type ModalStateType = 'off' | 'login' | 'signup' | 'confirmPassword';

export interface SnackbarState {
  isShowing: boolean;
  message: string;
}
