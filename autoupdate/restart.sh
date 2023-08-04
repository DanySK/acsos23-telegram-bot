#!/bin/sh
docker service create -e BOT_TOKEN=$BOT_TOKEN danysk/danysk/acsos23-telegram-bot:latest acsos23-telegram-bot
while true; do
    docker pull danysk/danysk/acsos23-telegram-bot:latest
    docker service update --image danysk/danysk/acsos23-telegram-bot:latest acsos23-telegram-bot 
    sleep 1m
done
