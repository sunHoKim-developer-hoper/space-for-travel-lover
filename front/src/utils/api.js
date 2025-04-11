const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export async function apiFetch(path, options = {}) {
  const token = localStorage.getItem("accessToken");
  const method = (options.method || "GET").toUpperCase();

  //formData인지 확인
  const isFormData = options.body instanceof FormData;
  //GET 요청인지 판별
  const shouldAttachBody = method !== "GET";

  const headers = {
    //spread 연산자(...)로 모든 헤더를 한 객체에 합치는 것이다.
    ...(options.headers || {}),
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
    //formdata일 땐 content-type을 안 보내야 한다.
    ...(shouldAttachBody && !isFormData
      ? { "Content-type": "application/json" }
      : {}),
  };

  const fetchOptions = {
    ...options,
    method,
    headers,
    ...(shouldAttachBody ? { body: options.body } : {}),
  };

  const response = await fetch(API_BASE_URL + path, fetchOptions);

  if (!response.ok) throw new Error("API 실패");
  else {
    const contentType = response.headers.get("content-type");
    //응답에 contentType이 있고 application/json이라면
    const isJson = contentType && contentType.includes("application/json");
    const body = isJson ? await response.json() : await response.text();
    return body;
  }
}
