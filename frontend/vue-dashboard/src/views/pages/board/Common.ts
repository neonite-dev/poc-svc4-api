//format 변경
export function formatBytes(bytes: number) {
  const scale = 1024
  const orders = ['GB', 'MB', 'KB', 'Bytes']
  let max = Math.pow(scale, orders.length - 1)

  for (const order of orders) {
    if (bytes > max) {
      return `${(bytes / max).toFixed(2)} ${order}`
    }
    max /= scale
  }

  return '0 Bytes'
}

//시간 변환
export function convertDateFormat(param: any) {
  const date = new Date(param)
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hours = date.getHours()
  const minutes = date.getMinutes()
  const seconds = date.getSeconds()
  const ampm = hours >= 12 ? '오후' : '오전'

  const formattedDate = `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`
  const formattedTime = `${ampm} ${hours % 12 || 12}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`

  return `${formattedDate} ${formattedTime}`
}

//목록 날짜 변환
export function convertDate(param: Date) {
  const year = param.getFullYear().toString()
  const month = (param.getMonth() + 1).toString().padStart(2, '0')
  const day = param.getDate().toString().padStart(2, '0')

  const formattedDate = `${year}-${month}-${day}`
  return formattedDate
}

//학급 변경
export function clsChange(cls: string): string {
  switch (cls) {
    case 'K':
      return '유아'
    case 'E':
      return '초등'
    case 'M':
      return '중고'
    case 'G':
      return '공통'
    default:
      return ''
  }
}

//UserInfo
export function getUserInfo() {
  const storageData = localStorage.getItem('user')
  const userInfo = storageData ? JSON.parse(storageData) : {}
  return userInfo
}
