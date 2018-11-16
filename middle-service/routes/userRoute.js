module.exports = function (app) {

    app.get('/login/:login', (req, res) => {
        let login = req.params.login;
        const userService = app.services.userService;

        userService.getUserTokenById(login)
            .then((token) => {
                res.send({auth: true, token: token})
            }).catch(() => {
                res.send({auth: false})
            })
    });

    app.post('/user', (req, res) => {
        const userService = app.services.userService;

        userService.createUser(req)
            .then((user) => {
                res.send({user: user})
            }).catch(() => {
            res.send({auth: false})
        })
    });

    app.put('/users', (req, res) => {
        const userService = app.services.userService;

        userService.updateUser(req, res)
            .then((user) => {
                res.send({user: user})
            }).catch(() => {
            res.send({auth: false})
        })
    });
};