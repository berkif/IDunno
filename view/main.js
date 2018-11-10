var inloop = false;
var desc = null;
var states = [];
var stateIndex = 0;
var commands = []; // FIXME logical conflict with stateIndex and steps
// derived structures
function drawInitial() {
    const params = document.getElementById('params');
    params.innerHTML = `
    <dt>gameLength (ms)</dt><dd>${desc.gameLength}</dd>
    <dt>mapSizeX (unit)</dt><dd>${desc.mapSizeX}</dd>
    <dt>mapSizeY (unit)</dt><dd>${desc.mapSizeY}</dd>
    <dt>commandSchedule (ms)</dt><dd>${desc.commandSchedule}</dd>
    <dt>internalSchedule (ms)</dt><dd>${desc.internalSchedule}</dd>
    <dt>broadcastSchedule (ms)</dt><dd>${desc.broadcastSchedule}</dd>`;
    const screen = document.getElementById('screen');
    const ctx = screen.getContext('2d');
    screen.width = desc.mapSizeX;
    screen.height = desc.mapSizeY;
    ctx.clearRect(0, 0, screen.width, screen.height);
    const players = document.getElementById('players');
    players.innerHTML = '';
    desc.players.forEach(player => {
        players.innerHTML += `<li>Id: ${player.userID}, Name: ${player.userName}</li>`;
    });
}
function fillStyle(obj) {
    const myName = 'idunno';
    return obj.owner != myName ? 'red' : 'black';
}
function drawState(state) {
    drawInitial();
    const screen = document.getElementById('screen');
    const ctx = screen.getContext('2d');
    const standings = document.getElementById('standings');
    standings.innerHTML = '';
    state.standings.forEach(standing => {
        standings.innerHTML +=
            `<li>Id: ${standing.userID}, ` +
            `Score: ${standing.score}` +
            `</li>;`
    });
    state.meteoriteStates.forEach(meteorite => {
        ctx.beginPath();
        ctx.arc(meteorite.meteoriteX, meteorite.meteoriteY, meteorite.meteoriteRadius, 0, 2 * Math.PI, false);
        ctx.fillStyle = 'grey';
        ctx.fill();
        ctx.lineWidth = 1;
        ctx.strokeStyle = '#333333';
        ctx.fillStyle = 'black';
        ctx.fillText(meteorite.meteoriteID, meteorite.meteoriteX, meteorite.meteoriteY);
        ctx.stroke();
        ctx.fillStyle = 'white';
    })
    state.rocketStates.forEach(rs => {
            ctx.font = '12px Arial';
            ctx.fillStyle = fillStyle(rs);
            ctx.textAlign = 'center';
            ctx.fillText(rs.rocketID, rs.rocketX, rs.rocketY - 15);
    })
    state.shipStates.forEach(ss => {
    
        ctx.fillStyle = fillStyle(ss);
        ctx.textAlign = 'center';
        ctx.fillText(ss.shipID, ss.shipX, ss.shipY - 15);
    })
}
function step(d) {
    const i = stateIndex + d;
    if (i < 0 || i >= states.length) {
        console.log(i);
        console.error('no more steps 😞');
        return true;
    }
    stateIndex = i;
    const index = document.getElementById('index');
    index.innerHTML = `${i + 1} / ${states.length}`;
    const state = states[i];
    drawState(state);
    return false;
}

function handleFileSelect(evt) {
    const file = evt.target.files[0];
    const reader = new FileReader();
    reader.onload = event => {
        const contents = event.target.result;
        const lines = contents.split('\n');
        lines.filter(line => !!line).forEach(line => {
            try {
                const message = JSON.parse(line).message;
                const data = JSON.parse(message);
                if (data.gameLength) {
                    desc = data;
                } else if (data.meteoriteStates) {
                    states.push(data);
                } else {
                    commands.push(data);
                }
            } catch (e) {
                console.error(e);
            }
        });
        step(0);
    };
    reader.readAsText(file);
}
function play() {
    const i = stateIndex;
    while(i < states.length && inloop) { 
    console.log('jajaja');
}
}
function handleBack(evt) {
    evt.target.disabled = step(-1);
}
function handleNext(evt) {
    evt.target.disabled = step(1);
}
document.getElementById('logfile').addEventListener('change', handleFileSelect, false);
document.getElementById('back').addEventListener('click', handleBack, false);
document.getElementById('next').addEventListener('click', handleNext, false);
function stepKey(e) {
    if ('37' == e.keyCode) {
        // left arrow
        step(-7);
    } else if ('39' == e.keyCode) {
        // right arrow
        step(7);
    }
}
document.onkeydown = stepKey;