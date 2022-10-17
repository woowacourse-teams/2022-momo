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

const getCategoryImage = (categoryId: GroupDetailData['categoryId']) => {
  return categoryImages[categoryId - 1];
};

const getCategoryIcon = (
  categoryId: GroupDetailData['categoryId'],
  svgSize: number,
) => {
  switch (categoryId) {
    case 1:
      return <StudySVG width={svgSize} height={svgSize} />;
    case 2:
      return <MogackoSVG width={svgSize} height={svgSize} />;
    case 3:
      return <SicsaSVG width={svgSize} height={svgSize} />;
    case 4:
      return <CafeSVG width={svgSize} height={svgSize} />;
    case 5:
      return <DrinkSVG width={svgSize} height={svgSize} />;
    case 6:
      return <ExerciseSVG width={svgSize} height={svgSize} />;
    case 7:
      return <GameSVG width={svgSize} height={svgSize} />;
    case 8:
      return <TravelSVG width={svgSize} height={svgSize} />;
    case 9:
      return <CultureSVG width={svgSize} height={svgSize} />;
    case 10:
      return <GuitarSVG width={svgSize} height={svgSize} />;
  }
};

export { getCategoryImage, getCategoryIcon };
