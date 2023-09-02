import axios from "axios";

export const URL = "우리 api 서버 url 예정";

const api = axios.create({
  baseURL: URL,
});

// 요청 시 인터셉트. access 토큰 있으면 넣을 예정
api.interceptors.request.use(
  async (config) => {
    // 액세스 토큰 넣기 코드 작성 예정
  },
  (error) => {
    // 에러 처리 코드 작성 예정
  }
);

// 응답 시 인터셉트. 만료시 갱신 시도 예정
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    // 갱신 시도 코드 작성 예정
  }
);

export default api;
