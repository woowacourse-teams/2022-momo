insert into momo_member(email, name, password)
values ('inbong@woowa.woo', '4기 이프', '1q2w3e4R!');
insert into momo_member(email, name, password)
values ('useji@woowa.woo', '4기 유세지', '1q2w3e4R!');
insert into momo_member(email, name, password)
values ('seongwon@woowa.woo', '4기 렉스', '1q2w3e4R!');
insert into momo_member(email, name, password)
values ('yukong@woowa.woo', '4기 유콩', '1q2w3e4R!');

insert into momo_group(category, deadline, capacity, description, enddate, startdate, host_id, location, name, isEarlyClosed)
values ('DRINK', '2022-07-22 17:59', 99, '오늘로 레벨 3의 두 번째 데모 데이가 끝납니다.\n저녁식사 하면서 간단하게 한 잔 하려고 하는데 오실 분들은 자유롭게 참여해주세요.\n메뉴는 치킨에 맥주입니다.\n술 강요 없음 / 딱 한 잔 가능 / 주종 선택 자유',
        '2022-07-22', '2022-07-22', 1, '둘둘치킨 선릉점', '오늘 끝나고 맥주 드실 분', false);

insert into momo_schedule(date, starttime, endtime, group_id) values ('2022-07-22', '18:00', '20:00', 1);

insert into momo_group(category, deadline, capacity, description, enddate, startdate, host_id, location, name, isEarlyClosed)
values ('ETC', '2022-07-21 23:59', 10, '주말에 같이 야구 보실 분 구합니다.\n외야 쪽 좌석으로 잡을 예정이고 두산이나 엘지 팬이시면 좋겠습니다.\n편하게 연락주세여 ⚾',
        '2022-07-23', '2022-07-23', 2, '잠실종합운동장', '주말에 같이 야구 봐요', false);

insert into momo_schedule(date, starttime, endtime, group_id) values ('2022-07-23', '13:00', '18:00', 2);

insert into momo_group(category, deadline, capacity, description, enddate, startdate, host_id, location, name, isEarlyClosed)
values ('STUDY', '2022-07-29 23:59', 7, 'Git 브랜칭 전략에 관심이 생겨서 한 달간 함께 스터디 하실 분을 모집합니다.\n기본적인 Git 사용법을 숙지하신 분들을 대상으로 진행하니 어느정도 다루시는 분들이 오시면 좋습니다.\n\n매주 토요일 저녁에 진행하고, 시간은 앞뒤로 조정 가능합니다.\n많은 관심 바랍니다.',
        '2022-08-27', '2022-07-30', 3, '선릉 테크살롱', 'Git 브랜칭 전략 스터디 모집', false);

insert into momo_schedule(date, starttime, endtime, group_id) values ('2022-07-30', '20:00', '21:00', 3);

insert into momo_group(category, deadline, capacity, description, enddate, startdate, host_id, location, name, isEarlyClosed)
values ('ETC', '2022-07-23 23:59', 10, '안녕하세요! 목요일에 같이 영화보러가요 🎞🎞',
        '2022-07-24', '2022-07-24', 4, 'CGV 용산아이파크몰', '탑건: 매버릭 같이 보실 분 계신가요', false);

insert into momo_schedule(date, starttime, endtime, group_id) values ('2022-07-24', '20:00', '21:00', 4);

