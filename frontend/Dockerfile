FROM node:14-alpine
WORKDIR /usr/src/app
CMD ["ng", "serve", "--host", "0.0.0.0"]
COPY package.json ./
RUN npm i -g @angular/cli@8.3.29 && npm i
COPY . .