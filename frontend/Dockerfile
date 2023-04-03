FROM node:12

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY .env ./

COPY . ./

EXPOSE 8081

CMD ["npm", "start"]