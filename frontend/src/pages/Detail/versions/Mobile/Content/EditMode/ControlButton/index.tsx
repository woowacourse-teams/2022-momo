import * as S from './index.styled';

interface ControlButtonProps {
  editGroup: () => void;
  finishEditMode: () => void;
}

function ControlButton({ editGroup, finishEditMode }: ControlButtonProps) {
  return (
    <S.Container>
      <S.EditButton type="button" onClick={editGroup}>
        편집하기
      </S.EditButton>
      <S.CancelButton type="button" onClick={finishEditMode}>
        취소하기
      </S.CancelButton>
    </S.Container>
  );
}

export default ControlButton;
