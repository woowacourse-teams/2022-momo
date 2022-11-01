const isEqualObject = (obj1: object, obj2: object): boolean => {
  return JSON.stringify(obj1) === JSON.stringify(obj2);
};

const copyObject = <T extends Object>(obj: T): T => {
  return JSON.parse(JSON.stringify(obj));
};

export { isEqualObject, copyObject };
