const preventUserSelect = `
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
`;

const oneLineEllipsis = `
  display: -webkit-box;

  overflow: hidden;
  text-overflow: ellipsis;

  word-wrap: break-word;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
`;

export { preventUserSelect, oneLineEllipsis };
