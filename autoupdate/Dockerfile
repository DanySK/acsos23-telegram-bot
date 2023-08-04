FROM docker:24.0.5-cli-alpine3.18
RUN mkdir /app
WORKDIR /app
COPY restart.sh restart.sh
RUN chmod +x restart.sh
ENTRYPOINT [ "/app/restart.sh" ]
