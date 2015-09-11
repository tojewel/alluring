Games = new Meteor.Collection("Games");

InstantMessages = new Meteor.Collection("InstantMessages");
OpenConversations = new Meteor.Collection("OpenConversations");

Meteor.users.allow({
    'remove': function () {
        return true;
    }
});

if (Meteor.isServer) {
    Meteor.startup(function () {
        Games.remove({})

        // Facebook
        ServiceConfiguration.configurations.remove({
            service: "facebook"
        });
        ServiceConfiguration.configurations.insert({
            service: "facebook",
            appId: "526041570861404",
            secret: "037d8c624e819e08c683e33a3b1b30c9"
        });

        // Robots
        Meteor.users.remove({robot: true})
        Meteor.users.insert({robot: true, profile: {name: "Garry Kasparov"}})
        Meteor.users.insert({robot: true, profile: {name: "Bobby Fischer"}})
    });

    Houston.add_collection(Games)
    Houston.add_collection(InstantMessages)
    Houston.add_collection(OpenConversations)
    Houston.add_collection(Meteor.users)
}



