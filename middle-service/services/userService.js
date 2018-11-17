const jwt = require('jsonwebtoken');

module.exports = function (app) {

    this.getUserTokenById = (login, password) => {
        const userClient = app.client.userClient;

        return userClient.getUserByLoginAndPassword(login, password)
            .then((response) => {
                let id = response.data.id;
                return jwt.sign({id}, process.env.SECRET, {
                    expiresIn: 300
                });
            }).catch((error) => {
                throw buildResponse(error.response.status, error.response.data.message)
            });
    };

    this.createUser = (req) => {
        const userClient = app.client.userClient;

        let accountType = req.body.accountType;
        let login = req.body.login;
        let password = req.body.password;
        let status = req.body.status;

        return userClient.createUser(accountType, login, password, status)
            .then((response) => {
                return buildResponse(response.status, response.data);
            }).catch((error) => {
                throw buildResponse(error.response.status, error.response.data.message)
            });
    };

    this.updateUser = (req, res) => {
        const tokenService = app.services.tokenService;
        const userClient = app.client.userClient;

        let accountType = req.body.accountType;
        let login = req.body.login;
        let password = req.body.password;
        let status = req.body.status;

        return tokenService.verifyToken(req, res)
            .then((userId) => {
                return userClient.updateUserById(accountType, login, password, status, userId)
            }).then((response) => {
                return buildResponse(response.status, response.data);
            }).catch((error) => {
                throw buildResponse(error.response.status, error.response.data.message)
            });
    };

    const buildResponse = (status, body) =>  {
        return {status: status, body: body}
    };

    return this;
};