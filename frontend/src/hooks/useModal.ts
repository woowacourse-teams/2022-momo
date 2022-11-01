import { useSetRecoilState } from 'recoil';

import { modalState } from 'store/states';

interface UseModalReturnType {
  setOffModal: () => void;
  showSignupModal: () => void;
  showLoginModal: () => void;
  showConfirmPasswordModal: () => void;
  showPostcodeModal: () => void;
  showThumbnailModal: () => void;
}

const useModal = (): UseModalReturnType => {
  const setModalFlag = useSetRecoilState(modalState);

  const setOffModal = (): void => {
    setModalFlag('off');
  };

  const showSignupModal = (): void => {
    setModalFlag('signup');
  };

  const showLoginModal = (): void => {
    setModalFlag('login');
  };

  const showConfirmPasswordModal = (): void => {
    setModalFlag('confirmPassword');
  };

  const showPostcodeModal = (): void => {
    setModalFlag('postcode');
  };

  const showThumbnailModal = (): void => {
    setModalFlag('thumbnail');
  };

  return {
    setOffModal,
    showSignupModal,
    showLoginModal,
    showConfirmPasswordModal,
    showPostcodeModal,
    showThumbnailModal,
  };
};

export default useModal;
