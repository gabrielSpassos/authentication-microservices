const axios = require('axios');
const characterServiceUrl = 'http://10.5.0.4:9001/character-service/api/users/';

module.exports = function () {

    this.createCharacter = (userId, charClass, charName) => {
        return axios({
            method:'post',
            url: characterServiceUrl + userId + '/characters',
            data: {
                charClass: charClass,
                name: charName
            }
        });
    };

    this.updateCharacter = (userId, charClass, charName) => {
        return axios({
            method:'put',
            url: characterServiceUrl + userId + '/characters/' + charName,
            data: {
                characterClassName: charClass
            }
        });
    };

    return this;
};