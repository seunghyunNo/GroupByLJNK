var api_key = "FB50E2191E8E06A7CA8BCC63648DEB93";

$(function(){
	loadGameList();
});

function loadGameList(){
	$.ajax({
		url:"/api/gameList",
		type: "GET",
        cache: false,
        success: function(data, status, xhr){
			if(status == "success"){
				parseJSON(data);
				return;
			}
        }
	});
}

// JSON 을 html로 뿌려주기
function parseJSON(data){
	const appDataList = JSON.parse(data).applist.apps.slice(0,99);
	$appList = $("#appList").children()
	const result = [];

	appDataList.forEach(element => {
		appId = element.appid;
		if(element.name.trim() != ''){
			result.push(`
			<div class="col-3 mb-3 card">
				<img class="img-thumbnail"
					src="https://cdn.cloudflare.steamstatic.com/steam/apps/${appId}/hero_capsule.jpg"
					alt="thumnail">
				<div class="row justify-content-between">
					<a class="col-auto text-decoration-none" href="/review/${appId}">${element.name}</a>
					<i class="col-2 bi bi-box2-heart fs-3"></i>
				</div>
			</div>
			`);
		}
	});
	$("#loading").remove();
	$appList.html(result.join('\n'));
}