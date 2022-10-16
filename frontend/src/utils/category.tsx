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
import {
  CafeSVG,
  CultureSVG,
  DrinkSVG,
  ExerciseSVG,
  GameSVG,
  GuitarSVG,
  MogackoSVG,
  SicsaSVG,
  StudySVG,
  TravelSVG,
} from 'assets/svg';
import { GroupDetailData } from 'types/data';

const categoryImages = [
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

const svgSize = 40;
const categoryIcons = [
  <StudySVG width={svgSize} height={svgSize} />,
  <MogackoSVG width={svgSize} height={svgSize} />,
  <SicsaSVG width={svgSize} height={svgSize} />,
  <CafeSVG width={svgSize} height={svgSize} />,
  <DrinkSVG width={svgSize} height={svgSize} />,
  <ExerciseSVG width={svgSize} height={svgSize} />,
  <GameSVG width={svgSize} height={svgSize} />,
  <TravelSVG width={svgSize} height={svgSize} />,
  <CultureSVG width={svgSize} height={svgSize} />,
  <GuitarSVG width={svgSize} height={svgSize} />,
];

const getCategoryImage = (categoryId: GroupDetailData['categoryId']) => {
  return categoryImages[categoryId - 1];
};

const getCategoryIcon = (categoryId: GroupDetailData['categoryId']) => {
  return categoryIcons[categoryId - 1];
};

export { getCategoryImage, getCategoryIcon };
