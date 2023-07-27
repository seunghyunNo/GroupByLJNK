let loadStatus = 0;
$(function(){
	loadGameList(0,50);

	let lastScroll = 0;
	let lastCnt = 0;
	let elementCnt = 50;

    $(document).scroll(function(e){
        //현재 높이 저장
        var currentScroll = $(this).scrollTop();
        //전체 문서의 높이
        var documentHeight = $(document).height();

        //(현재 화면상단 + 현재 화면 높이)
        var nowHeight = $(this).scrollTop() + $(window).height();

        //스크롤이 아래로 내려갔을때만 해당 이벤트 진행.
        if(currentScroll > lastScroll){
            //nowHeight을 통해 현재 화면의 끝이 어디까지 내려왔는지 파악가능
            //즉 전체 문서의 높이에 일정량 근접했을때 글 더 불러오기)
            if(documentHeight < (nowHeight + (documentHeight*0.05))){
                if (loadStatus == 0){
	                $("#loading").show();
	                loadStatus = 1
	                loadGameList(lastCnt, lastCnt + elementCnt);
					lastCnt += elementCnt;
                }
            }
        }
        //현재위치 최신화
        lastScroll = currentScroll;
    });

    let searchCnt = 0;

    $("#searchBtn").click(function(){
        if($("#searchInput").val().trim().length > 0){
            $("#appRow").empty();
            searchCnt = 1;
			$.ajax({
				url: "/api/gameList",
				type: "GET",
				cache: false,
				success: function(data, status){
					parseJsonFilter(data);
					return;
				}
			});
        }else{
            alert("검색어를 입력해주세요");
            if(searchCnt != 0) {
                $("#appRow").empty();
                loadGameList();
                searchCnt = 0;
            }
        }
    });
});

function loadGameList(startCnt, endCnt){
	$.ajax({
		url: "/api/gameList",
		type: "GET",
        cache: false,
        success: function(data, status){
			if(status == "success"){
				parseJSON(data, startCnt, endCnt);
				return;
			}
        }
	});
}

// JSON 을 html로 뿌려주기
function parseJSON(data, startCnt, endCnt){
	const appDataList = JSON.parse(data).applist.apps.slice(startCnt, endCnt);
	$appList = $("#appList").children()
	let result = [];
	$("#loading").hide();

	appDataList.forEach(element => {
		result = [];
		appId = element.appid;
		if(element.name.trim() != ''){
			result.push(`
			<div class="col-3 mb-3 card">
				<img class="img-thumbnail"
					src="https://cdn.cloudflare.steamstatic.com/steam/apps/${appId}/hero_capsule.jpg"
					alt="thumnail">
				<div class="row justify-content-between">
					<a class="col-auto col-s-8 text-decoration-none" href="/review/${appId}">${element.name}</a>
				</div>
			</div>
			`);
		}
		$appList.append(result.join('\n'));

	});
	loadStatus = 0;
}

function parseJsonFilter(data){
	const appDataList = JSON.parse(data).applist.apps;
    	$appList = $("#appList").children()
    	let result = [];
    	$("#loading").hide();
    	let value = $("#searchInput").val().trim().toLowerCase();

    	appDataList.forEach(element => {
    		result = [];
    		appId = element.appid;
    		if(element.name.trim() != ''){
    		    if(element.name.trim().toLowerCase().indexOf(value) >- 1){
	                result.push(`
	                <div class="col-3 mb-3 card">
	                    <img class="img-thumbnail"
	                        src="https://cdn.cloudflare.steamstatic.com/steam/apps/${appId}/hero_capsule.jpg"
	                        alt="thumnail">
	                    <div class="row justify-content-between">
	                        <a class="col-auto col-s-8 text-decoration-none" href="/review/${appId}">${element.name}</a>
	                    </div>
	                </div>
	                `);
    		    }
    		}
    		$appList.append(result.join('\n'));

    	});
}