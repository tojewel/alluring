initializing = true;
var board_context = {};

Template.gameboard.helpers({
    id: function () {
        return this._id;
    },

    fen: function () {
        var game = Games.findOne({_id: this._id}, {fields: {fen: 1}, _id: 0})
        var fen = game && game.fen
        if (board_context[this._id] != null) {
            board_context[this._id].update_fen(fen)
        }
        return fen
    }
})

Template.gameboard.rendered = function () {
    var g = this.data
    var me = Meteor.userId()
    var game_logic = new Chess(g.fen);

    var opponent_id = g.white === me ? g.black : (g.black === me ? g.white : null);
    var me_watching = opponent_id === null // nobody is opponent when watching

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
            Games.update({_id: g._id}, {$set: {fen: game_logic.fen()}})
        }
    }

    var onDragStart = function (source, piece) {
        if (g.game_over() === true ||
            (g.turn() === 'w' && piece.search(/^b/) !== -1) ||
            (g.turn() === 'b' && piece.search(/^w/) !== -1)) {
            return false;
        }
    };

    var board = new ChessBoard('board-' + g._id, {
        position: g.fen,
        showNotation: false,
        draggable: true,
        orientation: g.white === Meteor.userId() ? "white" : "black",

//        onDragStart: onDragStart,
        onDrop: makeMove
    });

    // Configure engine...
    var run_engine = function (fen) {
    }

    if (opponent_id) {
        var opponent_robot = Meteor.users.findOne({_id: opponent_id, 'profile.type': 'robot'}, {fields: {_id: 1}})
        if (opponent_robot) {
            var engineWorker = new Worker('engine.js');
            engineWorker.addEventListener('message', function (e) {
                var move = e.data;
                var from = move.substring(0, 2);
                var to = move.substring(2, 4);

                makeMove(from, to);
            }, false);

            var robot_side = g.white === me ? 'b' : 'w';

            run_engine = function (fen) {
                if (game_logic.turn() === robot_side) {
                    engineWorker.postMessage(fen);
                }
            }
        }
    }

    board_context[this.data._id] = {
        update_fen: function (fen) {
            game_logic.load(fen);
            board.position(fen);

            run_engine(fen);
        },

        kill: function () {
            board.destroy
        }
    }

    run_engine();

    if(!initializing) {
        $('#gamesTab a:last').tab('show')
    }
}

Template.gameboard.destroyed = function () {
    board_context[this.data._id].kill
    delete board_context[this.data._id]
}