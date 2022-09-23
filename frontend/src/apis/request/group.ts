import axios from 'apis/axios';
import { ERROR_MESSAGE } from 'constants/message';
import { API_PATH } from 'constants/path';
import { GROUP_RULE } from 'constants/rule';
import {
  CreateGroupData,
  GroupDetailData,
  GroupParticipants,
  GroupList,
  CategoryType,
  SelectableGroup,
} from 'types/data';
import { conditionalAuthenticationHeader } from 'utils/header';
import { accessTokenProvider } from 'utils/token';
import { makeUrl } from 'utils/url';

const requestCreateGroup = ({
  name,
  selectedCategory,
  capacity,
  startDate,
  endDate,
  schedules,
  deadline,
  location,
  description,
}: CreateGroupData) => {
  const data = {
    name,
    categoryId: selectedCategory.id,
    capacity: capacity || GROUP_RULE.CAPACITY.MAX,
    duration: {
      start: startDate,
      end: endDate,
    },
    schedules,
    deadline,
    location,
    description,
  };

  return axios
    .post<{ groupId: GroupDetailData['id'] }>(API_PATH.GROUP, data, {
      headers: {
        Authorization: `Bearer ${accessTokenProvider.get()}`,
      },
    })
    .then(response => {
      return response.data.groupId;
    })
    .catch(() => {
      throw new Error(ERROR_MESSAGE.CREATE.FAILURE_REQUEST);
    });
};

const requestEditGroup = (
  {
    name,
    selectedCategory,
    capacity,
    startDate,
    endDate,
    schedules,
    deadline,
    location,
    description,
  }: CreateGroupData,
  id: GroupDetailData['id'],
) => {
  const data = {
    name,
    categoryId: selectedCategory.id,
    capacity: capacity || GROUP_RULE.CAPACITY.MAX,
    duration: {
      start: startDate,
      end: endDate,
    },
    schedules,
    deadline,
    location,
    description,
  };

  return axios
    .put<{ groupId: GroupDetailData['id'] }>(`${API_PATH.GROUP}/${id}`, data, {
      headers: {
        Authorization: `Bearer ${accessTokenProvider.get()}`,
      },
    })
    .then(response => {
      return response.data.groupId;
    })
    .catch(() => {
      throw new Error(ERROR_MESSAGE.GROUP.FAILURE_EDIT_GROUP);
    });
};

const requestJoinedGroups =
  (
    type: SelectableGroup,
    pageNumber: number,
    excludeFinished: boolean,
    keyword: string,
  ) =>
  () => {
    const queryParams = {
      page: pageNumber,
      excludeFinished,
      keyword,
    };

    const baseUrl =
      type === 'participated'
        ? API_PATH.PARTICIPATED_GROUP
        : type === 'hosted'
        ? API_PATH.HOSTED_GROUP
        : API_PATH.LIKED_GROUP;

    return axios
      .get<GroupList>(makeUrl(baseUrl, queryParams), {
        headers: {
          Authorization: `Bearer ${accessTokenProvider.get()}`,
        },
      })
      .then(response => response.data);
  };

const requestGroups =
  (
    pageNumber: number,
    excludeFinished: boolean,
    keyword: string,
    categoryId: CategoryType['id'],
  ) =>
  () => {
    const queryParams = {
      page: pageNumber,
      excludeFinished,
      keyword,
      category: categoryId,
    };

    return axios
      .get<GroupList>(
        makeUrl(API_PATH.GROUP, queryParams),
        conditionalAuthenticationHeader(),
      )
      .then(response => response.data);
  };

const requestGroupDetail = (id: GroupDetailData['id']) => {
  return axios
    .get<GroupDetailData>(
      `${API_PATH.GROUP}/${id}`,
      conditionalAuthenticationHeader(),
    )
    .then(response => response.data);
};

const requestDeleteGroup = (id: GroupDetailData['id']) => {
  return axios.delete(`${API_PATH.GROUP}/${id}`, {
    headers: {
      Authorization: `Bearer ${accessTokenProvider.get()}`,
    },
  });
};

const requestGroupParticipants = (id: GroupDetailData['id']) => {
  return axios
    .get<GroupParticipants>(`${API_PATH.GROUP}/${id}${API_PATH.PARTICIPANTS}`)
    .then(response => response.data);
};

const requestJoinGroup = (id: GroupDetailData['id']) => {
  return axios.post(
    `${API_PATH.GROUP}/${id}${API_PATH.PARTICIPANTS}`,
    {},
    {
      headers: {
        Authorization: `Bearer ${accessTokenProvider.get()}`,
      },
    },
  );
};

const requestExitGroup = (id: GroupDetailData['id']) => {
  return axios.delete(`${API_PATH.GROUP}/${id}${API_PATH.PARTICIPANTS}`, {
    headers: {
      Authorization: `Bearer ${accessTokenProvider.get()}`,
    },
  });
};

const requestCloseGroup = (id: GroupDetailData['id']) => {
  return axios.post(
    `${API_PATH.GROUP}/${id}${API_PATH.CLOSE}`,
    {},
    {
      headers: {
        Authorization: `Bearer ${accessTokenProvider.get()}`,
      },
    },
  );
};

const requestLikeGroup = (id: GroupDetailData['id']) => {
  return axios.post(
    `${API_PATH.GROUP}/${id}${API_PATH.LIKE}`,
    {},
    {
      headers: {
        Authorization: `Bearer ${accessTokenProvider.get()}`,
      },
    },
  );
};

const requestUnlikeGroup = (id: GroupDetailData['id']) => {
  return axios.delete(`${API_PATH.GROUP}/${id}${API_PATH.LIKE}`, {
    headers: {
      Authorization: `Bearer ${accessTokenProvider.get()}`,
    },
  });
};

export {
  requestCreateGroup,
  requestEditGroup,
  requestJoinedGroups,
  requestGroups,
  requestGroupDetail,
  requestDeleteGroup,
  requestGroupParticipants,
  requestJoinGroup,
  requestExitGroup,
  requestCloseGroup,
  requestLikeGroup,
  requestUnlikeGroup,
};
