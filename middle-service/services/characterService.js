module.exports = function (app) {

    this.createCharacter = (req, res) => {
        const characterClient = app.client.characterClient;
        const tokenService = app.services.tokenService;

        let charClass = req.body.charClass;
        let charName = req.body.charName;

        return tokenService.verifyToken(req, res)
            .then((userId) => {
                return characterClient.createCharacter(userId, charClass, charName)
            }).then((response) => {
                return buildResponse(response.status, response.data);
            }).catch((error) => {
                throw buildResponse(error.response.status, error.response.data.message)
            });
    };

    this.updateCharacter = (req, res) => {
        const characterClient = app.client.characterClient;
        const tokenService = app.services.tokenService;

        let charClass = req.body.charClass;
        let charName = req.body.charName;

        return tokenService.verifyToken(req, res)
            .then((userId) => {
                return characterClient.updateCharacter(userId, charClass, charName)
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