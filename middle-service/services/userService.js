const q = require('q');
const jwt = require('jsonwebtoken');

module.exports = function (app) {

    this.getUserTokenById = (login) => {
        const deferred = q.defer();
        const userClient = app.client.userClient;

        userClient.getUserByLogin(login)
            .then((user) => {
                let id = user.id;
                let token = jwt.sign({id}, process.env.SECRET, {
                    expiresIn: 300
                });
                deferred.resolve(token);
                return token;
            })
            .catch((error) => {
                deferred.reject(() => {
                    return error;
                });
            });

        return deferred.promise;
    };

    this.createUser = (req, res) => {
        const deferred = q.defer();
        const tokenService = app.services.tokenService;
        const userClient = app.client.userClient;

        tokenService.verifyToken(req, res)
            .then((userId) => {
                return userClient.getUserById(userId);
            })
            .then((user) => {
                deferred.resolve(user);
                return user;
            });

        return deferred.promise;
    };

    return this;
};