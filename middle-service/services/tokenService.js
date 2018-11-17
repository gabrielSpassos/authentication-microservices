const jwt = require('jsonwebtoken');
const q = require('q');

module.exports = function() {

    this.verifyToken = (req, res) => {
        const deferred = q.defer();
        let token = req.headers['x-access-token'];

        if(!token) {
            deferred.resolve(res);
            return res.status(401).send(buildResponse(false, 401, 'Not informed user access token'));
        }

        jwt.verify(token, process.env.SECRET, (err, decoded) => {
            if(err) {
                deferred.resolve(res);
                return res.status(500).send(buildResponse(false, 500, 'Authentication failure'));
            }
            deferred.resolve(decoded.id);
            return decoded.id;
        });

        return deferred.promise;

    };

    const buildResponse = (auth, status, body) =>  {
        return {auth: auth, response: {status: status, body: body}}
    };

    return this;
};