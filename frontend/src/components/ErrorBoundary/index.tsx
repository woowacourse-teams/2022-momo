import { Component, ReactNode } from 'react';

import Lottie from 'lottie-react';

import error from 'assets/error.json';

import * as S from './index.styled';

interface ErrorBoundaryProps {
  children: ReactNode;
  isError?: boolean;
  fallbackUI?: ReactNode;
}

const style = {
  height: 300,
};

class ErrorBoundary extends Component<ErrorBoundaryProps> {
  state = { isError: false };

  static getDerivedStateFromError(
    _: Error,
  ): Pick<ErrorBoundaryProps, 'isError'> {
    return { isError: true };
  }

  componentDidCatch(error: Error) {
    this.setState({
      error,
    });
  }

  render() {
    if (this.state.isError) {
      return (
        <>
          {this.props.fallbackUI || (
            <S.Container>
              <Lottie animationData={error} style={style} />
              <S.Description>
                <S.Title>에러가 발생했어요 😥</S.Title>
                <p>
                  잠시 후 새로고침을 해보시고, 문제가 지속되면 관리자에게 문의
                  부탁드려요.
                </p>
              </S.Description>
            </S.Container>
          )}
        </>
      );
    }

    return <>{this.props.children}</>;
  }
}

export default ErrorBoundary;
