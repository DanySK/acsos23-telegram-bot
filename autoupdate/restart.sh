#!/bin/sh
docker service create -e BOT_TOKEN=$BOT_TOKEN danysk/acsos23-telegram-bot:latest acsos23-telegram-bot
while true; do
    docker pull danysk/acsos23-telegram-bot:latest
    docker service update --image danysk/acsos23-telegram-bot:latest acsos23-telegram-bot
    sleep 1m
done
