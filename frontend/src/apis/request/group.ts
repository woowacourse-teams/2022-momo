import { axios, axiosWithAccessToken } from 'apis/axios';
import { CLIENT_ERROR_MESSAGE } from 'constants/message';
import { API_PATH } from 'constants/path';
import { GROUP_RULE } from 'constants/rule';
import {
  CreateGroupData,
  GroupDetailData,
  GroupParticipants,
  GroupList,
  CategoryType,
  SelectableGroup,
  GroupSummary,
} from 'types/data';
import { makeUrl } from 'utils/url';

interface GroupIdResponse {
  groupId: GroupSummary['id'];
}

const makeGroupData = ({
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
  return {
    name,
    categoryId: selectedCategory.id === -1 ? 1 : selectedCategory.id,
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
};

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
  const data = makeGroupData({
    name,
    selectedCategory,
    capacity,
    startDate,
    endDate,
    schedules,
    deadline,
    location,
    description,
  });

  return axiosWithAccessToken
    .post<GroupIdResponse>(API_PATH.GROUP, data)
    .then(response => {
      return response.data.groupId;
    })
    .catch(error => {
      if (!error) {
        throw new Error(CLIENT_ERROR_MESSAGE.CREATE.FAILURE_REQUEST);
      }
      throw error;
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
  id: GroupSummary['id'],
) => {
  const data = makeGroupData({
    name,
    selectedCategory,
    capacity,
    startDate,
    endDate,
    schedules,
    deadline,
    location,
    description,
  });

  return axiosWithAccessToken
    .put<GroupIdResponse>(`${API_PATH.GROUP}/${id}`, data)
    .then(response => {
      return response.data.groupId;
    })
    .catch(error => {
      if (!error) {
        throw new Error(CLIENT_ERROR_MESSAGE.GROUP.FAILURE_EDIT_GROUP);
      }
      throw error;
    });
};

const requestEditThumbnail = (id: GroupSummary['id'], formData: FormData) => {
  return axiosWithAccessToken.post(
    `${API_PATH.GROUP}/${id}/thumbnail`,
    formData,
  );
};

const requestResetThumbnail = (id: GroupSummary['id']) => {
  return axiosWithAccessToken.delete(`${API_PATH.GROUP}/${id}/thumbnail`);
};

const requestJoinedGroups =
  (
    type: SelectableGroup,
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

    const baseUrl =
      type === 'participated'
        ? API_PATH.JOINED_GROUP.PARTICIPATED
        : type === 'hosted'
        ? API_PATH.JOINED_GROUP.HOSTED
        : API_PATH.JOINED_GROUP.LIKED;

    return axiosWithAccessToken
      .get<GroupList>(makeUrl(baseUrl, queryParams))
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

    return axiosWithAccessToken
      .get<GroupList>(makeUrl(API_PATH.GROUP, queryParams))
      .then(response => response.data);
  };

const requestGroupDetail = (id: GroupSummary['id']) => {
  return axiosWithAccessToken
    .get<GroupDetailData>(`${API_PATH.GROUP}/${id}`)
    .then(response => response.data);
};

const requestDeleteGroup = (id: GroupSummary['id']) => {
  return axiosWithAccessToken.delete(`${API_PATH.GROUP}/${id}`);
};

const requestGroupParticipants = (id: GroupSummary['id']) => {
  return axios
    .get<GroupParticipants>(`${API_PATH.GROUP}/${id}${API_PATH.PARTICIPANTS}`)
    .then(response => response.data);
};

const requestJoinGroup = (id: GroupSummary['id']) => {
  return axiosWithAccessToken.post(
    `${API_PATH.GROUP}/${id}${API_PATH.PARTICIPANTS}`,
  );
};

const requestExitGroup = (id: GroupSummary['id']) => {
  return axiosWithAccessToken.delete(
    `${API_PATH.GROUP}/${id}${API_PATH.PARTICIPANTS}`,
  );
};

const requestCloseGroup = (id: GroupSummary['id']) => {
  return axiosWithAccessToken.post(`${API_PATH.GROUP}/${id}${API_PATH.CLOSE}`);
};

const requestLikeGroup = (id: GroupSummary['id']) => {
  return axiosWithAccessToken.post(`${API_PATH.GROUP}/${id}${API_PATH.LIKE}`);
};

const requestUnlikeGroup = (id: GroupSummary['id']) => {
  return axiosWithAccessToken.delete(`${API_PATH.GROUP}/${id}${API_PATH.LIKE}`);
};

export {
  requestCreateGroup,
  requestEditGroup,
  requestEditThumbnail,
  requestResetThumbnail,
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
