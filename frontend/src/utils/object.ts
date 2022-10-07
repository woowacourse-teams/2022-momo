const isEqualObject = (obj1: object, obj2: object) => {
  return JSON.stringify(obj1) === JSON.stringify(obj2);
};

const copyObject = (obj: object) => {
  return JSON.parse(JSON.stringify(obj));
};

export { isEqualObject, copyObject };
