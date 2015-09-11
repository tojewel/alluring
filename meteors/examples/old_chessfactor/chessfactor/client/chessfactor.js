STARTING_POSITION_FEN = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1"
function start_new_game(w_player, b_player) {
    Games.insert({white: w_player, black: b_player, watching: [w_player, w_player], fen: STARTING_POSITION_FEN, moves: []});
}

Template.home.events({
    'click input[value=Yes]': function () {
        start_new_game(Meteor.userId(), this._id)
        InstantMessages.update(this._id, {$set: {ack: true}});
    },

    'click input[value=No]': function () {
        InstantMessages.update(this._id, {$set: {ack: true}});
    }
});

Template.home.helpers({
    'myMessages': function () {
        return InstantMessages.find({to: Meteor.userId(), ack: false})
    }
})

Template.games.events({
    'click input[value=Watch]': function () {
        Games.update(this._id, {$addToSet: {watching: Meteor.userId()}});
    }
});

Template.games.helpers({
    allLiveGames: function () {
        return Games.find({$where: "this.watching.length > 0"});
    }
})
