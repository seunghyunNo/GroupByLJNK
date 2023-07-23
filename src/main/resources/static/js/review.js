$(function(){
	loadScoreByUser();  // 평점 입력창을 로딩할지 말지 결정한다.
    loadScoreList();    // 평점 목록을 로딩한다.
	loadWishList();		// 찜 아이콘을 결정한다.
    
    // 평점 글자 수 제한
    $('#textBox').keyup(function (e) {
        let content = $(this).val();
        
        // 글자수 세기
        if (content.length == 0 || content == '') {
            $('#textCount').text('0');
        } else {
            $('#textCount').text(content.length);
        }
        
        // 글자수 제한
        if (content.length > 100) {
            // 100자 부터는 타이핑 되지 않도록
            $(this).val($(this).val().substring(0, 100));
            // 100자 넘으면 알림창 뜨도록
            alert('글자수는 100자까지 입력 가능합니다.');
        };
    });

    // ScoreBoard 등록
    $('#submitScore').click(function(){
        const score = $("#rangeScore").val() / 2;
        const content = $("#textBox").val().trim().replaceAll('\n','<br>');
        
        const data = {
            "app_id": appId,
            "score": score,
            "content": content,
        };

        $.ajax({
            url: "/score/write",
            type: "POST",
            data: data,
            cache: false,
            success: function(data, status){
                if(status !== "success"){
                    alert(data.status);
                    return;
                }
                alert(data.status);
                loadScoreList();
                loadScoreByUser();
            }
        });
    });

    // wishList Icon 클릭시
    $("#wishIcon").click(function(){
        const params = {
            "user_id": logged_id,
            "app_id": appId
        };
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

// 별점 코드    
const drawStar = (target) => {
    $(`.star span`).css({ width: `${target.value * 10}%` });
}

// 평점 목록 불러오기
function loadScoreList(){
    $.ajax({
        url: "/score/list/" + appId,
        type: "POST",
        caches: false,
        success: function(data, status){
            if(status == "success"){
                if(data.status !== "OK"){
                    alert(data.status);
                    return;
                }
                buildScore(data);
                addDelete();
            }
        }
    })
}

// 평점 랜더링하기
function buildScore(data){
    $("#scoreCnt").text(data.count);
    const result = [];

    data.data.forEach(element => {
        let username = element.user.username;
        let temp = element.score;
        let score = "★".repeat(temp) + "☆".repeat(5 - temp);
        let content = element.content;

        const delBtn = (logged_id !== element.userId) ? '' : `
		<i class="btn fa-solid fa-delete-left text-danger" data-bs-toggle="tooltip"
			data-del-userid="${element.userId}" title="삭제"></i>
		`;

        const row = `
        <tr>
            <td><span>${username}</span></td>
            <td><span>${score}</span></td>
            <td>
                <span>${content}</span>
                ${delBtn}
                </td>
        </tr>
        `;
        result.push(row);
    });

    $('#scoreList').html(result.join("\n"));
}

// 로그인 한 유저의 평점
function loadScoreByUser(){
    if(logged_id){
        const params = {
            "app_id": appId,
        };

        $.ajax({
            url: "/score/find",
            type: "POST",
            data: params,
            cache: false,
            success: function(data, status){
                if(status == "success"){
                    if(data.status == "OK"){
                        $('#inputScore').css("display", "none");
                    }else{
                        $('#inputScore').css("display", "block");
                    }
                }
            }
        });
    } else {
        return;
    }

}
function addScore(data){
    const obj = data.data[0];
    let username = obj.user.username;
    let score = obj.score;
    let content = obj.content;

    const delBtn = `
		<i class="btn fa-solid fa-delete-left text-danger" data-bs-toggle="tooltip"
			data-del-userid="${data.userId}" title="삭제"></i>
		`;

    const row = `
    <tr>
        <td><span>${username}</span></td>
        <td><span>${score}</span></td>
        <td>
            <span>${content}</span>
            ${delBtn}
            </td>
    </tr>
    `;

    $('#scoreList').prepend(row);

}

// 삭제 함수
function addDelete(){
    $('[data-del-userid]').click(function(){
        if(!confirm("댓글을 삭제하시겠습니까?")) return;

        const userId = $(this).attr("data-del-userId");

        const params = {
            "app_id": appId,
            "user_id": userId,
        }

        $.ajax({
            url: "/score/delete",
            data: params,
            type: "POST",
            cache: false,
            success: function(data, status){
                if(status == "success"){
                    if(data.status !== "OK"){
						alert(data.status);
						return;
					}
                }
                alert(data.status);
                loadScoreList();
                loadScoreByUser();
            }
        });
    });
}

//
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