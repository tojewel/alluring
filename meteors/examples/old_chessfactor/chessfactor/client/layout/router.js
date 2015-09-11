Router.configure({
    layoutTemplate: 'layout'
});

Router.map(function () {
    this.route('home', {path: '/'});

    this.route('players');
    this.route('player', {
        path: '/player/:_id',
        data: function () {
            return Meteor.users.findOne(this.params._id);
        }
    });

    this.route('games');
    this.route('game', {
        path: '/games/:_id',
        data: function () {
            return Games.findOne(this.params._id);
        }
    });
});

Template.layout.helpers({
    'myMessage': function () {
        return InstantMessages.find();
    },

    'playersCount': function () {
        return Meteor.users.find().count();
    },

    'gamesCount': function () {
        return Games.find().count();
    }
})