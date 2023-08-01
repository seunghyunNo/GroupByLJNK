$(function(){
	// 글 [삭제] 버튼
	$("#btnDel").click(function(){
	let answer = confirm("삭제하시겠습니까?");
		if(answer){
			$("form[name='frmDelete']").submit();
		}
	});

	// 현재 글의 id
	const id = $("input[name='id']").val().trim();

	// 현재 글 댓글 불러오기
	loadComment(id);

	// 추천 상태 확인하기
	loadRecBtn(id);

	$("#btn_comment").click(function(){
		// 입력한 댓글
		const content =$("#input_comment").val().trim();
		console.log(content);

		if(!content){
			alert("내용을 입력해주세요");
			$("#input_comment").focus();
			return;
		}
		// parameter
		const data={
			"board_id":id,
			"user_id":logged_id,
			"content":content
		};

		$.ajax({
			url:"/comment/write",
			type:"POST",
			data:data,
			cache:false,
			success:function(data,status,xhr){
				if(status =="success"){
					if(data.status !== "ok"){
						alert(data.status);
						return;
					}
					loadComment(id);
					$("#input_comment").val('');
				}
			}
		});
	});

	$("#btnRec").click(function() {
		console.log(logged_id);
		console.log(id);
		const requestData  = {
			"board_id": id
		};
		$.ajax({
			url: "/recommend/board",
			type: "GET",
			data: requestData ,
			cache: false,
			success: function(responseData, status, xhr) {
				if (status === "success") {
					// 이 부분은 서버에서 응답으로 받은 데이터를 어떻게 처리할지에 따라 수정해야 합니다.
					// 현재 data에는 서버에서 보낸 데이터가 들어있을 것입니다.
					// 아래는 예시로, data 배열을 루프 돌면서 user_id를 확인하고 처리하는 코드입니다.
					for (const e of responseData) {
						if (e.user_id === logged_id ) {
							deleteRecommend(logged_id, id);
							$('#btnRec').removeClass('btn-primary');
                            $('#btnRec').addClass('btn-outline-primary');
                            $('#btnRec i').removeClass('bi-hand-thumbs-up-fill');
                            $('#btnRec i').addClass('bi-hand-thumbs-up');
							return;
						}
					}
					addRecommend(logged_id, id);
					$('#btnRec').removeClass('btn-outline-primary');
                    $('#btnRec').addClass('btn-primary');
                    $('#btnRec i').removeClass('bi-hand-thumbs-up');
                    $('#btnRec i').addClass('bi-hand-thumbs-up-fill');
					return;
				}
			},
			error: function(xhr, status, error) {
				console.log("Error:", error);
			}
		});
	});
});

// 글의 댓글 목록 불러오기
function loadComment(board_id){
	$.ajax({
		url:"/comment/list?id=" + board_id,
		type:"GET",
		cache:false,
		success:function(data,status,xhr){
			if(status =="success"){
				if(data.status !=="ok"){
					alert(data.status);
					return;
				}
				buildComment(data);
				addDelete();
			}
		}
	});
}

function buildComment(result){
	$("#cmt_cnt").text(result.count);   // 댓글 갯수

	const out=[];

	result.data.forEach(comment=>{

		let id = comment.id;
		let content = comment.content;
		let regdate = comment.regdate;

		let user_id = comment.user.id;
		let username = comment.user.username;
		let name = comment.user.name;

		// 삭제 버튼 여부
		const delBtn=(logged_id !==user_id) ? '':`
			<i class="btn fa-solid fa-delete-left text-danger" data-bs-toggle="tooltip"
			data-cmtdel-id="${id}" title="삭제"></i>
		`;

		// 수정 필요함
		const row=`
			<tr>
			<td><span><strong>${username}</strong></span></span></td>
			<td>
			<span>${content}</span>
			${delBtn}
			</td>
			<td><span><small class="text-secondary">${regdate}</small></span></td>
			</tr>
		`;
		out.push(row);
	});
	$("#cmt_list").html(out.join("\n"));
}

function addDelete(){
	const id = $("input[name='id']").val().trim();

	$("[data-cmtdel-id]").click(function(){
		if(!confirm("댓글을 삭제하시겠습니까?")) return;

		const comment_id=$(this).attr("data-cmtdel-id");

		$.ajax({
			url:"/comment/delete",
			type:"POST",
			cache:false,
			data:{"id":comment_id},
			success:function(data,status,xhr){
				if(status == "success"){
					if(data.status !== "ok"){
						alert(data.status);
						return;
					}
					loadComment(id);
				}
			},
		});
	});
}

function addRecommend(logged_id, board_id){
	console.log("add_logged:"+ logged_id);
	console.log("add_board:" + board_id);

	const data = {
		"user_id": logged_id,
		"board_id": board_id
	};

	$.ajax({
		url:"/recommend/addRecommend",
		type:"POST",
		cache:false,
		data: data,
		data: data,
		success:function(data,status,xhr){
			if(data.status !=="ok"){
				alert("추천하셨습니다.");
				return;
			}
		}
	});
}

function deleteRecommend(logged_id, board_id){
	const data = {
	"user_id": logged_id,
	"board_id": board_id
	};

	$.ajax({
		url:"/recommend/deleteRecommend",
		type:"POST",
		cache:false,
		data: data,
		success:function(data,status,xhr){
			if(data.status !=="ok"){
				alert("추천을 취소하셨습니다");
				return;
			}
		}
	});
}

function loadRecBtn(id){
	$.ajax({
		url: "/recommend/board",
		type: "GET",
		data: {"board_id": id},
		cache: false,
		success: function(responseData, status, xhr) {
			if (status === "success") {
				for (const e of responseData) {
					console.log("e.user_id", e.user_id);
					console.log("logged_id", logged_id);
					console.log("\n");
					if (e.user_id == logged_id ) {
						$('#btnRec').removeClass('btn-outline-primary');
                        $('#btnRec').addClass('btn-primary');
                        $('#btnRec i').removeClass('bi-hand-thumbs-up');
                        $('#btnRec i').addClass('bi-hand-thumbs-up-fill');
                        return;
					}
				}
				$('#btnRec').removeClass('btn-primary');
                $('#btnRec').addClass('btn-outline-primary');
                $('#btnRec i').removeClass('bi-hand-thumbs-up-fill');
                $('#btnRec i').addClass('bi-hand-thumbs-up');
			}
		}
	});
}