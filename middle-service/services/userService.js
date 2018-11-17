const q = require('q');
const jwt = require('jsonwebtoken');

module.exports = function (app) {

    this.getUserTokenById = (login, password) => {
        const userClient = app.client.userClient;

        return userClient.getUserByLoginAndPassword(login, password)
            .then((user) => {
                let id = user.id;
                return jwt.sign({id}, process.env.SECRET, {
                    expiresIn: 300
                });
            }).catch((error) => {
                throw buildResponse(error.response.status, error.response.data.message)
            });
    };

    this.createUser = (req) => {
        const deferred = q.defer();
        const userClient = app.client.userClient;

        let accountType = req.body.accountType;
        let login = req.body.login;
        let password = req.body.password;
        let status = req.body.status;

        userClient.createUser(accountType, login, password, status)
            .then((user) => {
                deferred.resolve(user);
                return user;
            });

        return deferred.promise;
    };

    this.updateUser = (req, res) => {
        const deferred = q.defer();
        const tokenService = app.services.tokenService;
        const userClient = app.client.userClient;

        let accountType = req.body.accountType;
        let login = req.body.login;
        let password = req.body.password;
        let status = req.body.status;

        tokenService.verifyToken(req, res)
            .then((userId) => {
                return userClient.updateUserById(accountType, login, password, status, userId)
            })
            .then((user) => {
                deferred.resolve(user);
                return user;
            });

        return deferred.promise;
    };

    const buildResponse = (status, body) =>  {
        return {status: status, body: body}
    };

    return this;
};