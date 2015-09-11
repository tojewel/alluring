Router.configure({
    layoutTemplate: 'masterLayout',
    loadingTemplate: 'loading',
    notFoundTemplate: 'pageNotFound',
    yieldTemplates: {
        nav: {to: 'nav'},
        footer: {to: 'footer'}
    }
});

Router.map(function() {
    this.route('players', {
        onBeforeAction: AccountsTemplates.ensureSignedIn,
        path: '/'
    });

    this.route('games', {
        onBeforeAction: AccountsTemplates.ensureSignedIn
    });

    this.route('clubs', {
        onBeforeAction: AccountsTemplates.ensureSignedIn
    });

    this.route('private', {
        onBeforeAction: AccountsTemplates.ensureSignedIn
    });
});
