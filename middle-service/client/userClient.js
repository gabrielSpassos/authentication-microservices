const axios = require('axios');
const userServiceUrl = 'http://10.5.0.3:9000/user-service/api/v1/users';

module.exports = function () {

    this.getUserByLoginAndPassword = (login, password) => {
        return axios({
            method:'get',
            url: userServiceUrl + '/login/' + login,
            headers: {
                password: password
            }
        });
    };

    this.createUser = (accountType, login, password, status) => {
        return axios({
            method:'post',
            url: userServiceUrl,
            data: {
                accountType: accountType,
                login: login,
                password: password,
                status: status
            }
        });
    };

    this.updateUserById = (accountType, login, password, status, userId) => {
        return axios({
            method:'put',
            url: userServiceUrl + '/' + userId,
            data: {
                accountType: accountType,
                login: login,
                password: password,
                status: status
            }
        });
    };

    return this;
};