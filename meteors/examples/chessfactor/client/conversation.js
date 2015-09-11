Template.openConversations.openConversations = function () {
    return OpenConversations.find({to: Mteor.userId()})
}

Template.conversation.helpers({

})

Template.conversation.events({
    'keypress input[type=text]': function (e, template) {
        if (e.which === 13) {
            console.log("ha ha " + template.find("input[type=text]").value)
            template.find("input[type=text]").value = ""
        }
    }
})