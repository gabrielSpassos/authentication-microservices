module.exports = function (app) {

    app.get('/login/:login', (req, res) => {
        let login = req.params.login;
        let password = req.headers['password'];

        if(!password){
            return res.status(401).send(buildResponseBody(false, 'Not informed password'));
        }

        const userService = app.services.userService;

        userService.getUserTokenById(login, password)
            .then((token) => {
                res.send(buildResponseBody(true, token))
            }).catch((error) => {
                res.status(error.status).send(buildResponseBody(false, error));
            });
    });

    app.post('/users', (req, res) => {
        const userService = app.services.userService;

        userService.createUser(req)
            .then((response) => {
                res.status(response.status).send(buildResponseBody(false, response))
            }).catch((error) => {
                res.status(error.status).send(buildResponseBody(false, error));
            });
    });

    app.put('/users', (req, res) => {
        const userService = app.services.userService;

        userService.updateUser(req, res)
            .then((response) => {
                res.status(response.status).send(buildResponseBody(true, response))
            }).catch((error) => {
                res.status(error.status).send(buildResponseBody(false, error));
            });
    });

    const buildResponseBody = (auth, response) => {
        return {auth: auth, response}
    }
};