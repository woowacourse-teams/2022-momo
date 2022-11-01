const preventBubbling = (e: React.MouseEvent<HTMLElement>): void => {
  e.stopPropagation();
};

export { preventBubbling };
