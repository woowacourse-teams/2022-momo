import { Address } from 'react-daum-postcode';

export type RoadAddress = {
  address_name: string;
  region_1depth_name: string;
  region_2depth_name: string;
  region_3depth_name: string;
  road_name: string;
  underground_yn: 'Y' | 'N';
  main_building_no: string;
  sub_building_no: string;
  building_name: string;
  zone_no: string;
  x: string;
  y: string;
};

export type GeocoderResult = Array<{
  address_name: string;
  address_type: 'REGION' | 'ROAD' | 'REGION_ADDR' | 'ROAD_ADDR';
  x: string;
  y: string;
  address: Address;
  road_address: RoadAddress;
}>;

export enum GeocoderStatus {
  ERROR = 'ERROR',
  OK = 'OK',
  ZERO_RESULT = 'ZERO_RESULT',
}
