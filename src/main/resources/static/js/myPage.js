var api_key = "FB50E2191E8E06A7CA8BCC63648DEB93";

$(function() {
    loadGameList();
});

function loadGameList() {
    $.ajax({
        url: "/api/gameList",
        type: "GET",
        cache: false,
        success: function(data, status, xhr) {
            if (status === "success") {
                wishList(function(myWishList) {
                    parseJSON(data, myWishList); // Pass myWishList to parseJSON function
                });
            }
        }
    });
}

function wishList(callback) {
    $.ajax({
        url: "/user/wishList",
        type: "GET",
        dataType: "JSON",
        cache: false,
        success: function(data, status, xhr) {
            if (status === "success") {
                const myWishList = data; // Parse the JSON data here
                callback(myWishList); // Pass myWishList to the callback function
            }
        }
    });
}

function parseJSON(data, myWishList) {
    const appDataList = JSON.parse(data).applist.apps;
    const $appList = $("#appList").children();
    const result = [];

    appDataList.forEach(element => {
        const appId = element.appid;
        myWishList.forEach(wish => {
            if (appId == wish) {
                result.push(`
                    <div class="col-4 mb-3 card">
                        <img class="img-thumbnail"
                            src="https://cdn.cloudflare.steamstatic.com/steam/apps/${appId}/hero_capsule.jpg"
                            alt="thumnail">
                        <div class="row justify-content-between">
                            <a class="col-auto" href="/review/${appId}">${element.name}</a>
                        </div>
                    </div>
                `);
            }
        });
    });

    $("#loading").remove();
    $appList.html(result.join('\n'));
}
