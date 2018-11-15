const axios = require('axios');
const q = require('q');
const userServiceUrl = 'http://localhost:9000/user-service/api/v1/users';

module.exports = function () {

    this.getUserByLogin = (login) => {
        const deferred = q.defer();

        axios({
            method:'get',
            url: userServiceUrl + '/login/' + login,
        }).then((response) => {
            deferred.resolve(response.data);
            return response.data;
        }).catch(() => {
            deferred.reject(() => {
               return 'Fail';
            });
        });

        return deferred.promise;
    };

    this.getUserById = (id) => {
        const deferred = q.defer();

        axios({
            method:'get',
            url: userServiceUrl + '/' + id,
        }).then((response) => {
            deferred.resolve(response.data);
            return response.data;
        }).catch(() => {
            deferred.reject(() => {
                return 'Fail';
            });
        });

        return deferred.promise;
    };

    return this;
};