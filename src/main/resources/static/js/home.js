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
				console.log(data);
				return;
			}
        }
	});
}
