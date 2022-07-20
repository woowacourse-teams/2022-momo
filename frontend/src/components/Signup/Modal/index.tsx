import { useRecoilState } from 'recoil';

import Modal from 'components/Modal';
import { modalState } from 'store/states';

function SignupModal() {
  const [isModalOpen, setModalState] = useRecoilState(modalState);

  const setOffModal = () => {
    setModalState('off');
  };

  return (
    <Modal modalState={isModalOpen === 'signup'} setOffModal={setOffModal}>
      회원가입 모달이에용
    </Modal>
  );
}

export default SignupModal;
