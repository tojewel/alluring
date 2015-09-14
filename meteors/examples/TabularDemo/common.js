//code shared between client and server

TestCaseResult = new Mongo.Collection("TestCaseResult");

TabularTables = {};

Meteor.isClient && Template.registerHelper('TabularTables', TabularTables);

TabularTables.TestCaseResult = new Tabular.Table({
    name: "BookList",
    collection: TestCaseResult,
    columns: [
        {data: "name", title: "name"},
        {data: "status", title: "status"},
        {data: "failure", title: "failure"}
    ]
});

if (Meteor.isClient) {
    Template.table.helpers({
        'count': function () {
            return TestCaseResult.find({}).count();
        }
    })
}