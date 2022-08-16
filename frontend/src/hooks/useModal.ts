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

  return {
    setOffModal,
    showSignupModal,
    showLoginModal,
    showConfirmPasswordModal,
  };
};

export default useModal;
