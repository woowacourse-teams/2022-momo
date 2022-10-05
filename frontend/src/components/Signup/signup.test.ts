import { checkValidId, checkValidName, checkValidPassword } from './validate';

describe('회원가입 창의 입력값을 검증할 수 있다.', () => {
  it('입력한 아이디의 길이가 적절하지 않으면 false를 반환한다.', () => {
    const tooShortId = 'abc';
    const tooLongId = 'thisIdLengthIsFiftyOneAAAAAAAAAAAAAAAAAAAAAAAAAAAAA';

    expect(checkValidId(tooShortId)).toBe(false);
    expect(checkValidId(tooLongId)).toBe(false);
  });

  it('입력한 닉네임의 길이가 적절하지 않으면 false를 반환한다.', () => {
    const tooShortName = '';
    const tooLongName = 'thisNameLengthIsThirtyOneAAAAAA';

    expect(checkValidName(tooShortName)).toBe(false);
    expect(checkValidName(tooLongName)).toBe(false);
  });

  it('입력한 패스워드가 적절하지 않으면 false를 반환한다.', () => {
    const tooShortPassword = 'abc123!';
    const tooLongPassword = 'abcdefg1234567!!!';
    const notContainNumberPassword = 'abcdefg!!';
    const notContainEnglishPassword = '1234567!!';
    const notContainSpecialCharactersPassword = 'abcde12345';

    expect(checkValidPassword(tooShortPassword)).toBe(false);
    expect(checkValidPassword(tooLongPassword)).toBe(false);
    expect(checkValidPassword(notContainNumberPassword)).toBe(false);
    expect(checkValidPassword(notContainEnglishPassword)).toBe(false);
    expect(checkValidPassword(notContainSpecialCharactersPassword)).toBe(false);
  });

  it('입력한 아이디의 길이가 적절하면 true를 반환한다.', () => {
    const wellformedId = 'test123';

    expect(checkValidId(wellformedId)).toBe(true);
  });

  it('입력한 닉네임의 길이가 적절하면 true를 반환한다.', () => {
    const wellformedName = '유세지';

    expect(checkValidName(wellformedName)).toBe(true);
  });

  it('입력한 패스워드가 적절하지 않으면 true를 반환한다.', () => {
    const wellformedPassword = '1q2w3e!!';

    expect(checkValidPassword(wellformedPassword)).toBe(true);
  });
});
