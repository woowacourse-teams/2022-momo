import {
  CategoryImage1,
  CategoryImage2,
  CategoryImage3,
  CategoryImage4,
  CategoryImage5,
  CategoryImage6,
  CategoryImage7,
  CategoryImage8,
  CategoryImage9,
  CategoryImage10,
} from 'assets/category';
import { GroupDetailData } from 'types/data';

const categoryImage = [
  CategoryImage1,
  CategoryImage2,
  CategoryImage3,
  CategoryImage4,
  CategoryImage5,
  CategoryImage6,
  CategoryImage7,
  CategoryImage8,
  CategoryImage9,
  CategoryImage10,
];

const getCategoryImage = (categoryId: GroupDetailData['categoryId']) => {
  return categoryImage[categoryId - 1];
};

export { getCategoryImage };
