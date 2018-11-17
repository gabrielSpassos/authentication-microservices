module.exports = function (app) {

    app.post('/characters', (req, res) => {
        const characterService = app.services.characterService;

        characterService.createCharacter(req, res)
            .then((response) => {
                res.status(response.status).send(buildResponseBody(true, response))
            }).catch((error) => {
                res.status(error.status).send(buildResponseBody(false, error));
            })
    });

    app.put('/characters', (req, res) => {
        const characterService = app.services.characterService;

        characterService.updateCharacter(req, res)
            .then((response) => {
                res.status(response.status).send(buildResponseBody(true, response))
            }).catch((error) => {
                res.status(error.status).send(buildResponseBody(false, error));
            })
    });

    const buildResponseBody = (auth, response) => {
        return {auth: auth, response}
    }
};