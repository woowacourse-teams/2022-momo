import { useEffect } from 'react';

import { GroupDetailData } from 'types/data';
import { GeocoderResult, GeocoderStatus } from 'types/kakaomap';

const useKakaoMap = (location: GroupDetailData['location']) => {
  useEffect(() => {
    const getMap = () => {
      const { kakao } = window;

      const options = {
        center: new kakao.maps.LatLng(37.5152723282151, 127.102994947008),
        level: 3,
      };

      const mapElement = document.getElementById('map');

      if (!mapElement) return;

      const map = new kakao.maps.Map(mapElement, options);

      const geocoder = new kakao.maps.services.Geocoder();

      if (location.address) {
        // 주소로 좌표 검색
        geocoder.addressSearch(
          location.address,
          function (result: GeocoderResult, status: GeocoderStatus) {
            if (status === kakao.maps.services.Status.OK) {
              const coords = new kakao.maps.LatLng(result[0].y, result[0].x);

              const marker = new kakao.maps.Marker({
                map: map,
                position: coords,
              });

              const infoWindow = new kakao.maps.InfoWindow({
                content: `<div style="padding:5px;">${result[0].road_address.building_name}</div>`,
                removable: true,
              });

              kakao.maps.event.addListener(marker, 'mouseover', function () {
                infoWindow.open(map, marker);
              });
              kakao.maps.event.addListener(marker, 'mouseout', function () {
                infoWindow.close();
              });
              kakao.maps.event.addListener(map, 'click', () => {
                window.open(
                  `https://map.kakao.com/link/map/${result[0].road_address.building_name},${result[0].y},${result[0].x}`,
                );
              });

              map.setCenter(coords);
            }
          },
        );
      }
    };

    getMap();
  }, [location]);
};

export default useKakaoMap;
