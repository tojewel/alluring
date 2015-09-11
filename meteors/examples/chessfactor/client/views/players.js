Template.players.helpers({
    'players': function () {
        return Meteor.users.find();
    },

    'not_me': function () {
        return Meteor.userId() != this._id;
    }
})

play_white = false
STARTING_POSITION_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
function start_new_game(w_player, b_player) {
    Games.insert({white: w_player, black: b_player, watching: [w_player, b_player], fen: STARTING_POSITION_FEN, moves: []});
}

Template.players.events({
    'click input[value=Play]': function () {
        if (this.profile.type === 'robot') {
            if (play_white) {
                Games.insert({white: Meteor.userId(), black: this._id, watching: [Meteor.userId()], fen: STARTING_POSITION_FEN, moves: []});
            } else {
                Games.insert({white: this._id, black: Meteor.userId(), watching: [Meteor.userId()], fen: STARTING_POSITION_FEN, moves: []});
            }
            play_white = !play_white
        } else {
            InstantMessages.insert({from: Meteor.userId(), to: this._id, msg: "Wanna play a game?", ack: false});
        }
    },

    // TODO: refactor this later on...
    'click input[value=Talk]': function () {
        if (OpenConversations.find({userId: Meteor.userId()}).count === 0) {
            OpenConversations.insert({userId: Meteor.userId()}, {with: []})
        }

        OpenConversations.update({userId: Meteor.userId()}, {$addToSet: {with: this._id}})
    }
});