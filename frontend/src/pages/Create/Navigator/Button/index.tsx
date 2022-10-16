import useHover from 'hooks/useHover';

import Dot from './Dot';
import * as S from './index.styled';

interface ButtonProps {
  content: string;
  number: number;
  isFocused: boolean;
  changePage: (pageNumber: number) => () => void;
}

function Button({ content, number, isFocused, changePage }: ButtonProps) {
  const { isHover, changeHoverState } = useHover();

  return (
    <S.Container>
      {isHover && (
        <S.Modal
          onMouseOver={changeHoverState(true)}
          onMouseOut={changeHoverState(false)}
        >
          {content}
        </S.Modal>
      )}
      <button
        type="button"
        onClick={changePage(number)}
        onMouseOver={changeHoverState(true)}
        onMouseOut={changeHoverState(false)}
      >
        <Dot isFocused={isFocused} />
      </button>
    </S.Container>
  );
}

export default Button;
