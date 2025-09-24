export function urlParamParser(param: any) {
  if (param && typeof param === 'object') {
    return Object.entries(param)
      .map(([key, value]: any) => encodeURIComponent(key) + '=' + encodeURIComponent(value))
      .join('&')
  }
  return param
}
