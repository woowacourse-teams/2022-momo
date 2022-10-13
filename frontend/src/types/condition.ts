export type ModalStateType =
  | 'off'
  | 'login'
  | 'signup'
  | 'confirmPassword'
  | 'postcode';

export interface SnackbarState {
  isShowing: boolean;
  message: string;
}
