import { useSetRecoilState } from 'recoil';

import { modalState } from 'store/states';

const useModal = () => {
  const setModalFlag = useSetRecoilState(modalState);

  const setOffModal = () => {
    setModalFlag('off');
  };

  const showSignupModal = () => {
    setModalFlag('signup');
  };

  const showLoginModal = () => {
    setModalFlag('login');
  };

  const showConfirmPasswordModal = () => {
    setModalFlag('confirmPassword');
  };

  const showPostcodeModal = () => {
    setModalFlag('postcode');
  };

  const showThumbnailModal = () => {
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
