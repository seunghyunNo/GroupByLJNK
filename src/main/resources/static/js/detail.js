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
  loadComment(id)


  $("#btn_comment").click(function(){
        // 입력한 댓글
      const content =$("#input_comment").val().trim();


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
                  $("#input_comment").val('')
              }
          }
      })
  });
});

// 글의 댓글 목록 불러오기
function loadComment(board_id){
  $.ajax({
      url:"/comment/list   // 예상 틀린 곳
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

    let user_id = comment.user_id;
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
            <td><span><strong>${username}</strong><br><small class="text-secondary">(${name})</small></span></span></td>
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
