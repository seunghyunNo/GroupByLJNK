$(function(){
	loadWishList();
	loadAvgScore();
	// wishList Icon 클릭시
	$("#wishIcon").click(function(){
		const params = {
			"user_id": logged_id,
			"app_id": appId
		};
		console.log(logged_id);

		if(logged_id){
			$.ajax({
				url: "/wishlist/check",
				type: "POST",
				data: params,
				cache: false,
				success: function(data, status){
					if(status == 'success'){
						if(data.count){
							deleteWishList();
						} else {
							writeWishList();
						}
					}
				}
			});
		} else {
			alert("로그인 후 이용가능합니다")
		}

	});
});
// 찜 목록 아이콘 모양 결정
function loadWishList(){

    const params = {
        "user_id": logged_id,
        "app_id": appId
    };

	$.ajax({
		url: "/wishlist/check",
        type: "POST",
        data: params,
        cache: false,
        success: function(data, status){
            if(status == "success"){
                if(data.count == 0){
                    $('#wishIcon').removeClass('bi-clipboard-heart-fill');
                    $('#wishIcon').addClass('bi-clipboard-heart');
                    return false;
                } else {
                    $('#wishIcon').removeClass('bi-clipboard-heart');
                    $('#wishIcon').addClass('bi-clipboard-heart-fill');
                    return true;
                }
            }
        }
	});
}

// 찜목록 추가
function writeWishList(){

    const params = {
        "user_id": logged_id,
        "app_id": appId
    };

    $.ajax({
        url: "/wishlist/write",
        type: "POST",
        data: params,
        cache:false,
        success: function(data, status){
            if(status == "success"){
                loadWishList();
                alert("찜 목록에 추가되었습니다")
            }
        }
    });
}

// 찜 목록 삭제
function deleteWishList(){
    const params = {
        "user_id": logged_id,
        "app_id": appId
    }

    $.ajax({
        url: "/wishlist/delete",
        type: "POST",
        data: params,
        cache:false,
        success: function(data, status){
            if(status == "success"){
                loadWishList();
                alert("찜 목록에서 삭제되었습니다")
            }
        }
    });
}

// 소수점 잡아주는 함수
const getFloatFixed = (value, fixed) => {
	return parseFloat(Math.round(value * 100) / 100).toFixed(fixed);
};

// 평균 구해오는 함수
function loadAvgScore(){
	$.ajax({
		url: "/score/avg",
		type: "POST",
		data: {"app_id": appId},
		cache: false,
		success: function(data, status){
			if(status == "success"){
				$("#score_avg b").text(getFloatFixed(data.avg, 2));
			}
		}
	})
}