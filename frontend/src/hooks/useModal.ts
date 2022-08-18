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

  const showGroupEditModal = () => {
    setModalFlag('groupEdit');
  };

  return {
    setOffModal,
    showSignupModal,
    showLoginModal,
    showConfirmPasswordModal,
    showGroupEditModal,
  };
};

export default useModal;
