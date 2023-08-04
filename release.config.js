var publishCmd = `
echo "$DOCKER_PASSWORD" | docker login -u danysk --password-stdin
git tag -a -f \${nextRelease.version} \${nextRelease.version} -F CHANGELOG.md || exit 1
git push --force origin \${nextRelease.version} || exit 2
docker build -t danysk/acsos23-telegram-bot:\${nextRelease.version} . || exit 3
docker push danysk/acsos23-telegram-bot:\${nextRelease.version} || exit 4
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
