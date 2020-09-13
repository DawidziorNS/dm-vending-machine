// import { getExtraAuthorizationHeaders } from 'config/config';
// import AuthorizationException from 'utils/exception/authorizationException';
// import NotFoundException from 'utils/exception/notFoundException';

export const NOT_FOUND_ERROR = 'common.errorMessage.notFound';



export const fetchResponseHandler = (response, isJsonResponseBody) => {
  if (response.ok) {
    return isJsonResponseBody ? response.json() : response.text();
  }
  return undefined;
};

export const simpleFetchResponseHandler = response => {
  if (response.ok) {
    return true;
  }
  return undefined;
};

export const httpFetch = (url, method = 'GET', signal = undefined) =>
  fetch(url, {
    signal,
    credentials: 'same-origin',
    method
  });

const httpPOST = (url, body) =>
  fetch(url, {
    credentials: 'same-origin',
    method: 'POST',
    headers: {
      'content-type': 'application/json'
    },
    body
  });

export const cleanedJsonFetch = (
  url,
  responseHandler,
  errorHandler,
  method = 'GET'
) => {
  const abortController = new AbortController();
  const { signal } = abortController;
  httpFetch(url, method, signal)
    .then(res => fetchResponseHandler(res, true))
    .then(result => responseHandler(result))
    .catch(e => errorHandler(e));

  return function cleanUp() {
    abortController.abort();
  };
};

export const simpleJsonFetch = (url, method = 'GET', signal = undefined) =>
  httpFetch(url, method, signal).then(res => fetchResponseHandler(res, true));
export const simpleTextFetch = (url, method = 'GET') =>
  httpFetch(url, method).then(res => fetchResponseHandler(res, false));
export const simplePostJsonFetch = (url, body) =>
  httpPOST(url, body).then(res => fetchResponseHandler(res, true));
export const simplePostTextFetch = (url, body) =>
  httpPOST(url, body).then(res => fetchResponseHandler(res, false));
export const simpleFetch = (url, method = 'GET') =>
  httpFetch(url, method).then(simpleFetchResponseHandler);
