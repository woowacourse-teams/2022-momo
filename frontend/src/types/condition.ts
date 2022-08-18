export type ModalStateType =
  | 'off'
  | 'login'
  | 'signup'
  | 'confirmPassword'
  | 'groupEdit';

export interface SnackbarState {
  isShowing: boolean;
  message: string;
}
