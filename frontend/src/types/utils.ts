export type ArrElement<ArrType> = ArrType extends readonly (infer ElementType)[]
  ? ElementType
  : never;

export type ObjectKey<T extends object> = keyof T;

export type PartialOptional<T, K extends keyof T> = Omit<T, K> &
  Partial<Pick<T, K>>;
