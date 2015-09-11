Template.watchingGames.helpers({
    'watchingGames': function () {
        return Games.find({watching: Meteor.userId()});
    }
});

Template.watchingGames.events({
    'click input[value=Done]': function () {
        Games.update(this._id, {$pull: {watching: Meteor.userId()}});
    }
});

Template.watchingGames.rendered = function () {
    $('#gamesTab a:last').tab('show')
    initializing = false;
    console.log("rendered .....")
}