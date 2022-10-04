import DaumPostcode, { Address } from 'react-daum-postcode';
import { useRecoilValue } from 'recoil';

import Modal from 'components/Modal';
import useModal from 'hooks/useModal';
import { modalState } from 'store/states';

const postCodeStyle = {
  height: '500px',
};

interface PostcodeProps {
  completeFunc: (address: Address) => void;
}

function Postcode({ completeFunc }: PostcodeProps) {
  const modalFlag = useRecoilValue(modalState);
  const { setOffModal } = useModal();

  const complete = (address: Address) => {
    completeFunc(address);
    setOffModal();
  };

  return (
    <Modal modalState={modalFlag === 'postcode'}>
      <DaumPostcode onComplete={complete} style={postCodeStyle} />
    </Modal>
  );
}

export default Postcode;
