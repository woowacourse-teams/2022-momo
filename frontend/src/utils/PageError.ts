class PageError extends Error {
  targetPageNumber: number;

  constructor(message: string, targetPageNumber: number) {
    super(message);
    this.targetPageNumber = targetPageNumber;
  }
}

export default PageError;
