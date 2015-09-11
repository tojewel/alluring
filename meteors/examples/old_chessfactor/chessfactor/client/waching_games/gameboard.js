var board_context = {}

Template.gameboard.helpers({
    id: function () {
        return this._id;
    },

    fen: function () {
        var game = Games.findOne({_id: this._id}, {fields: {fen: 1}, _id: 0})
        fen = game && game.fen
        if (board_context[this._id] != null) {
            board_context[this._id].update_fen(fen)
        }
        return fen
    }
})

Template.gameboard.rendered = function () {
    var game = this.data;
    var computer_side = game.white === Meteor.userId() ? 'b' : 'w';
    var game_logic = new Chess(game.fen);

    var engineWorker = new Worker('engine.js');

    function makeMove(source, target) {

        // see if the move is legal
        var move = game_logic.move({
            from: source,
            to: target,
            promotion: 'q' // NOTE: always promote to a queen for example simplicity
        });

        // illegal move
        if (move === null) {
            return 'snapback';
        } else {
            Games.update({_id: game._id}, {$set: {fen: game_logic.fen()}})
        }
    }

    var board = new ChessBoard('board-' + game._id, {
        position: game.fen,
        showNotation: false,
        draggable: true,
        orientation: game.white === Meteor.userId() ? "white" : "black",

        onDrop: makeMove
    });

    engineWorker.addEventListener('message', function (e) {
        var move = e.data;
        var from = move.substring(0, 2);
        var to = move.substring(2, 4);

        makeMove(from, to);
    }, false);

    function run_engine () {
        if (game_logic.turn() === computer_side) {
            engineWorker.postMessage(fen);
        }
    }

    board_context[this.data._id] = {
        update_fen: function (fen) {
            game_logic.load(fen);
            board.position(fen);

            run_engine();
        },

        kill: function () {
            board.destroy
        }
    }

    run_engine();
}

Template.gameboard.destroyed = function () {
    board_context[this.data._id].kill
    delete board_context[this.data._id]
}