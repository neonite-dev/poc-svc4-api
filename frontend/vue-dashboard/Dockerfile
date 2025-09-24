FROM node:20-slim as build-stage
RUN mkdir -p /app
WORKDIR /app
COPY package*.json ./
RUN npm install -g pnpm
RUN pnpm install
COPY . .
RUN pnpm run build

# set nginx 
FROM nginx:stable-alpine as production-stage
RUN rm -rf /etc/nginx/conf.d/default.conf
RUN rm -rf /usr/share/nginx/html/*
COPY ./nginx/default.conf /etc/nginx/conf.d/
COPY --from=build-stage /app/dist /usr/share/nginx/html/vueapp2

EXPOSE 5000
CMD ["nginx", "-g","daemon off;"]
# docker pull nginx
# docker build -t vue_app:latest .
# docker run --name vueapp -p 5000:5000 vue_app:latest
