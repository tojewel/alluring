Template.nav.helpers({
    signInOut: function () {
        var key = Meteor.user() ? 'signOut' : 'signIn';
        return T9n.get(key);
    },

    'gamesCount': function () {
        return Games.find({ $where: "this.watching.length > 0" }).count();
    },

    'playersCount': function () {
        return Meteor.users.find({}).count();
    }
});

Template.nav.events({
    'click #nav-signinout': function (event) {
        if (Meteor.user())
            Meteor.logout();
        else
            Router.go('atSignIn');
    }
});