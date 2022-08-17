import * as S from './index.styled';

interface CheckboxProps {
  description: string;
  checked: boolean;
  toggleChecked: () => void;
}

function Checkbox({ description, checked, toggleChecked }: CheckboxProps) {
  return (
    <S.Label onClick={toggleChecked}>
      <S.Checkbox className={checked ? 'checked' : ''}>
        {checked && <S.Check />}
      </S.Checkbox>
      {description}
    </S.Label>
  );
}

export default Checkbox;
