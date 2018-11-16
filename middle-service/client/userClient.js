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

    this.createUser = (accountType, login, password, status) => {
        const deferred = q.defer();

        axios({
            method:'put',
            url: userServiceUrl,
            data: {
                accountType: accountType,
                login: login,
                password: password,
                status: status
            }
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

    this.updateUserById = (accountType, login, password, status, userId) => {
        const deferred = q.defer();

        axios({
            method:'put',
            url: userServiceUrl + '/' + userId,
            data: {
                accountType: accountType,
                login: login,
                password: password,
                status: status
            }
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