var publishCmd = `
echo "$DOCKER_PASSWORD" | docker login -u danysk --password-stdin
git tag -a -f \${nextRelease.version} \${nextRelease.version} -F CHANGELOG.md || exit 1
git push --force origin \${nextRelease.version} || exit 2
docker build -t danysk/acsos23-telegram-bot:\${nextRelease.version} -t danysk/acsos23-telegram-bot:latest . || exit 3
docker push --all-tags danysk/acsos23-telegram-bot || exit 4
docker build -t danysk/acsos23-telegram-bot-autoupdate:\${nextRelease.version} -t danysk/acsos23-telegram-bot-autoupdate:latest autoupdate || exit 5
docker push --all-tags danysk/acsos23-telegram-bot-autoupdate || exit 6
`
var config = require('semantic-release-preconfigured-conventional-commits');
config.plugins.push(
    [
        "@semantic-release/exec",
        {
            "publishCmd": publishCmd,
        }
    ],
    "@semantic-release/github",
    "@semantic-release/git",
)
module.exports = config
