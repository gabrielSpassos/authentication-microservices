module.exports = function (app) {

    app.get('/login/:login', (req, res) => {
        let login = req.params.login;
        let password = req.headers['password'];

        if(!password){
            return res.status(401).send({auth: false, message: "Not informed password"});
        }

        const userService = app.services.userService;

        userService.getUserTokenById(login, password)
            .then((token) => {
                res.send({auth: true, token: token})
            }).catch(() => {
                res.send({auth: false})
            })
    });

    app.post('/users', (req, res) => {
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
            }).catch((error) => {
                console.log(error);
                res.send({auth: false, message: 'Error'})
            })
    });
};