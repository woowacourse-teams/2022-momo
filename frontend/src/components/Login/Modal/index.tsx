import { useRecoilState } from 'recoil';

import Modal from 'components/Modal';
import { modalState } from 'store/states';

function LoginModal() {
  const [modalFlag, setModalFlag] = useRecoilState(modalState);

  const setOffModal = () => {
    setModalFlag('off');
  };

  return (
    <Modal modalState={modalFlag === 'login'} setOffModal={setOffModal}>
      로그인 모달이에용
    </Modal>
  );
}

export default LoginModal;
