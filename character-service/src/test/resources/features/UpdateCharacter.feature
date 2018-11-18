@UpdateChar
Feature: Update Character

  Scenario: Update character with success
    Given user id "5beeddf9271c06542585634d"
    And character name "TestChar"
    And character class "UpdatedMage"
    And this user is a user with normal permissions and already have one char
    And find an user with the id "5beeddf9271c06542585634d" with status 200
    When update the user character
    Then must return the updated character
    And a response status equals to 200

  Scenario: Fail to update character, but user doesn't exists
    Given user id "999"
    And character name "TestChar"
    And character class "UpdatedMage"
    And this user is a user not found user
    And find an user with the id "999" with status 400
    When update the user character
    Then must return error, because the user isn't found
    And a response status equals to 400

  Scenario: Fail to update character, character doesn't exists
    Given user id "5beeddf9271c06542585634d"
    And character name "nonexistentChar"
    And character class "UpdatedMage"
    And this user is a user with normal permissions and already have one char
    And find an user with the id "5beeddf9271c06542585634d" with status 200
    When update the user character
    Then must return error, because the character doesn't exists
    And a response status equals to 400

  Scenario: Update character with success, user premium
    Given user id "hgghjasasduy1231"
    And character name "Merlin"
    And character class "UpdatedMage"
    And this user is a user with premium permissions and two chars
    And find an user with the id "hgghjasasduy1231" with status 200
    When update the user character
    Then must return the updated character
    And a response status equals to 200