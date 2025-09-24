# MilkT Free Vue Material UI Admin Template

MilkT is a free Material UI admin dashboard template built with Vue. It is meant to provide the best possible User Experience with highly customizable feature-rich pages. It is a complete Dashboard Template that has easy and intuitive responsive design whether it is viewed on retina screens or laptops.

:star: :star: :star: Do not forget to star (Top right of this page) it if you like the theme :star: :star: :star:

## Why MilkT?

MilkT offers everything you need to create dashboards. We have included the following high-end features in our initial release:

- Modern aesthetics UI design
- Vuetify components
- Fully Responsive, all modern browser supported
- Easy to use code structure
- Flexible & High-Performance code
- Easy Documentation Guide

## Technology Stack

- [Vuetify 3](https://vuetifyjs.com/)
- Vite
- Vue3

## Husky 설정

- .git Root 경로가 vue-dashboard 상위에 있을경우 .git의 최상위 경로 이동 후 설치
  -> package.json 정의 - "prepare": "cd ../../ && husky frontend/vue-dashboard/.husky" - root일경우 => "prepare": "husky"
- .husky 내부 pre-commit, commit-msg, post-merge 추가 생성 및 .git root 변경시 파일 내부 경로 수정
  -> root 가 아닐 경우 cd frontend/vue-dashboard && 추가
  -> cd frontend/vue-dashboard && pnpm lint-staged
  -> cd frontend/vue-dashboard && pnpm install
