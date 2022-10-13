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

  return {
    setOffModal,
    showSignupModal,
    showLoginModal,
    showConfirmPasswordModal,
    showPostcodeModal,
  };
};

export default useModal;
