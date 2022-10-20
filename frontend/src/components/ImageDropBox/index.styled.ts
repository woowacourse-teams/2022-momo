import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const labelDescriptionAnimation = keyframes`
  0%, 100% {
    opacity: 1;
  }

  50% {
    opacity: 0.2;
  }
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 1.5rem;

  width: 90%;
`;

const Title = styled.h2`
  font-size: 1.2rem;

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      font-size: 1.1rem;
    }
  `}
`;

const Label = styled.label<{ isDragOver: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: 15rem;

  border-radius: 8px;

  font-size: 1rem;

  cursor: pointer;

  p {
    animation: ${labelDescriptionAnimation} 3s infinite;
  }

  ${({ theme: { colors }, isDragOver }) => `
    background: ${isDragOver && `${colors.gray006}`};
    color: ${colors.gray001};
    border: 1px solid ${colors.gray003};
  `}
`;

const Image = styled.img`
  width: 100%;
  height: 100%;
  object-fit: contain;
`;

const FileInput = styled.input`
  position: absolute;
  width: 0;
  height: 0;
  padding: 0;
  overflow: hidden;
  border: 0;
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Button = styled.button`
  width: 8rem;
  height: 2.5rem;

  border-radius: 8px;

  font-size: 1rem;
`;

const ResetButton = styled(Button)`
  ${({ theme: { colors } }) => `
    border: 1px solid ${colors.green001};
    color: ${colors.green001};
  `}
`;

const EditButton = styled(Button)`
  ${({ theme: { colors } }) => `
    background: ${colors.green001};
    color: ${colors.white001};
  `}
`;

export {
  Form,
  Title,
  Label,
  Image,
  FileInput,
  ButtonContainer,
  ResetButton,
  EditButton,
};
