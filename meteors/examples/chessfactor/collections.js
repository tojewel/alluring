Games = new Meteor.Collection("Games");

InstantMessages = new Meteor.Collection("InstantMessages");
OpenConversations = new Meteor.Collection("OpenConversations");

AccountsTemplates.init();

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

        // Google
        ServiceConfiguration.configurations.remove({
            service: "google"
        });
        ServiceConfiguration.configurations.insert({
            service: "google",
            clientId: "960015956408-cj56ckqdplhmj5g0gg8b24ba3smnqi48.apps.googleusercontent.com",
            secret: "Ahr1aVAzStfL9l9cM5sLWPbH",
            loginStyle: "popup"
        });

        // Robots
        Meteor.users.remove({'profile.type': {$in: ['robot', 'tester']}})

        Meteor.users.insert({profile: {name: "Garry Kasparov", type: "robot"}})
        Meteor.users.insert({profile: {name: "Bobby Fischer", type: "robot"}})

//        Meteor.users.insert({profile: {name: "Pamela Anderson", type: "tester"}})
//        Meteor.users.insert({profile: {name: "Tome Lee", type: "tester"}})
    });

    //Houston.add_collection(Games)
    //Houston.add_collection(InstantMessages)
    //Houston.add_collection(OpenConversations)
    //Houston.add_collection(Meteor.users)
}