export type ModalStateType =
  | 'off'
  | 'login'
  | 'signup'
  | 'confirmPassword'
  | 'groupEdit'
  | 'postcode';

export interface SnackbarState {
  isShowing: boolean;
  message: string;
}
