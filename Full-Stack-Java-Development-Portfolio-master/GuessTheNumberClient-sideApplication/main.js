let games = [];
let rounds = [];

function gtnFetchGames() {
    event.preventDefault();
    fetch("http://localhost:8080/api/game")
        .then((response) => response.json())
        .then((r) => {
            r.forEach((game) => {
                games.push(game);
            });
            $("#TableArea1 ").slideUp();
            generateTable(games, "t1");
            $("#TableArea1 ").slideDown();
            games = [];
        })
        .catch((err) => console.log("fail: " + err));
}

function gtnFetchRounds() {
    event.preventDefault();
    $("#RoundsByGameId").attr("placeholder", "Game Id:");
    let gameId = $("#RoundsByGameId").val();

    if ($("#RoundsByGameId").val() != "") {
        fetch("http://localhost:8080/api/rounds/" + gameId)
            .then((response) => {
                if (response.status == 400) {
                    console.log("ERROR 400: ");
                    response.text().then((r) => console.log(r));
                    $("#TableArea2").slideUp();
                    $("#MessageBoard").text(
                        "No Guess Have Been Made for Game Number " +
                            gameId +
                            ". You could be the first!"
                    );
                } else {
                    response.json().then((r) => {
                        r.forEach((round) => {
                            rounds.push(round);
                        });
                        $("#TableArea2").slideUp();
                        generateTable(rounds, "t2");
                        $("#TableArea2").slideDown();
                        rounds = [];
                    });
                }
            })
            .catch((err) => console.log("fail: " + err));
    } else {
        $("#TableArea2").slideUp();
        $("#RoundsByGameId").attr("placeholder", "MISSING FIELD!");
    }
}

function gtnFetchGuess() {
    event.preventDefault();
    $("#gameId").attr("placeholder", "Game Id:");
    $("#guess").attr("placeholder", "Guess:");
    let gameId = $("#gameId").val();
    let guess = $("#guess").val();
    const data = { gameId: gameId, userGuess: guess };

    if ($("#gameId").val() != "" && $("#guess").val() != 0) {
        fetch("http://localhost:8080/api/guess", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },

            body: JSON.stringify(data),
        })
            .then((response) => {
                if (response.status == 400) {
                    console.log("ERROR 400: ");
                    response.text().then((r) => {
                        console.log(r);
                        $("#MessageBoard").text(r);
                    });
                } else {
                    response
                        .json()
                        .then((guess) =>
                            $("#MessageBoard").text(
                                "Your Guess: " +
                                    `${guess.userGuess}` +
                                    "  |   " +
                                    "Your Score: " +
                                    `${guess.result}`
                            )
                        );
                }
            })
            .catch((err) => console.log("fail: " + err + "----"));
    } else {
        $("#gameId").attr("placeholder", "MISSING FIELD!");
        $("#guess").attr("placeholder", "MISSING FIELD!");
    }
}

function getGameById() {
    event.preventDefault();
    $("#gameIdToSee").attr("placeholder", "Game Id:");
    let gameIdInput = $("#gameIdToSee").val();

    if (gameIdInput != "") {
        fetch("http://localhost:8080/api/game/" + gameIdInput)
            .then((response) => {
                if (response.status == 404) {
                    console.log("Error 404: PAGE NOT FOUND ");
                    $("#MessageBoard").text(
                        "Can't find that game! Tip: Check update list of games"
                    );
                } else {
                    response.json().then((game) => {
                        $("#MessageBoard").text(
                            "Game Numer: " +
                                `${game.gameId}` +
                                "  |   Solution: " +
                                `${game.correctSolution}`
                        );
                    });
                }
            })
            .catch((err) => console.log("fail: " + err));
    } else {
        $("#gameIdToSee").attr("placeholder", "MISSING FIELD!");
    }
}

function createNewGame() {
    event.preventDefault();

    fetch("http://localhost:8080/api/begin", { method: "POST" })
        .then((response) => response.json())
        .then((id) =>
            $("#MessageBoard").text(
                "Game Numer " + `${id}` + " successfully created"
            )
        );
}

function generateTable(arr, tableId) {
    $("#" + tableId).html(
        "<thead> <tr id=" +
            tableId +
            "Head> </tr> </thead><tbody id=" +
            tableId +
            "Body></tbody>"
    );
    makeTableHead(arr[0], tableId);
    arr.forEach((e) => makeTableRow(e, tableId));
}

function makeTableHead(data, tableId) {
    let keys = Object.keys(data);
    for (i in keys) {
        $("#" + tableId + "Head").append("<th>" + keys[i] + "</th>");
    }
}

function makeTableRow(e, tableId) {
    $("#" + tableId + "Body").append("<tr>");
    for (j in e) {
        $("#" + tableId + "Body").append("<td>" + e[j] + "</td>");
    }
    $("#" + tableId + "Body").append("</tr>");
}

$(document).ready(function () {
    $("#TableArea1 ").hide();
    $("#TableArea2").hide();

    $("#HideAllGames").click(() => {
        $("#TableArea1 ").slideUp();
    });
    $("#HideAllRounds").click(() => {
        $("#TableArea2 ").slideUp();
    });

    $("#GuessForm").submit(() => {
        gtnFetchGuess();
        $("#GuessForm")[0].reset();
    });
    $("#RequestGameDetailsForm").submit(() => {
        getGameById();
        $("#RequestGameDetailsForm")[0].reset();
    });
    $("#RequestRoundsForm").submit(() => {
        gtnFetchRounds();
        $("#RequestRoundsForm")[0].reset();
    });

    $("#GetAllGamesButton").click(gtnFetchGames);
    $("#CreateNewGameButton").click(createNewGame);
});
