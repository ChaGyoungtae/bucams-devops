FROM nginx:latest
LABEL maintainer="village1031 <village1031@gmail.com>"
LABEL version="1.0"
COPY dist /usr/share/nginx/html
COPY default.conf /etc/nginx/conf.d/default.conf
ENV TZ=Asia/Seoul
EXPOSE 80
CMD [ "nginx", "-g", "daemon off;" ]