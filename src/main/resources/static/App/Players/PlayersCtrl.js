'use strict';
angular.module('mundialitoApp').controller('PlayersCtrl', ['$scope', '$log', 'PlayersManager', 'players', 'Alert', function ($scope, $log, PlayersManager, players, Alert) {
    $scope.players = players;
    $scope.showNewPlayer = false;
    $scope.newPlayer = null;

    $scope.addNewPlayer = function () {
        $scope.newPlayer = PlayersManager.getEmptyPlayerObject();
    };

    $scope.saveNewPlayer = function() {
        PlayersManager.addPlayer($scope.newPlayer).then(function(data) {
            Alert.success('Player was added successfully');
            $scope.newPlayer = null;
            $scope.players.push(data);
        });
    };

    $scope.deletePlayer = function(player) {
        var scope = player;
        player.delete().success(function() {
            Alert.success('Player was deleted successfully');
            $scope.players.splice($scope.players.indexOf(scope),1);
        })
    };

    $scope.schema =  PlayersManager.getPlayerSchema();
}]);