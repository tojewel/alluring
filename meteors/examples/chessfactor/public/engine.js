var cinnamonCommand = null;

function findMove(fen) {
    if(cinnamonCommand === null) {
        importScripts('cinnamon_engine_1_2b.js');
        cinnamonCommand = Module.cwrap('command', 'string', ['string', 'string'])
    }
    cinnamonCommand("setMaxTimeMillsec", "300")
    cinnamonCommand("position", fen)
    return cinnamonCommand("go", "")
}

addEventListener('message', function(e) {
    postMessage(findMove(e.data));
}, false);
