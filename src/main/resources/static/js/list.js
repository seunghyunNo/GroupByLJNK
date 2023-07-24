$(function(){
    $("[name='pageRows']").change(function(){
     var form = $("[name='frmPageRows']");
     form.attr("method","POST");
     form.attr("action","pageRows");
     form.submit();
    });

        const userId = $("#loginUser").text();
        const appId = $("#appId").val().trim();
        var check = 0;
        $("[data-recommend-btn]").click(function(){
         let btn = $(this).attr("data-recommend-btn");
         let boardId = $(this).parent().siblings().children("span").attr("data-fileboard-recommend");
         let input = $(this).parent().siblings().children("input");
         let rec = $(this).parent().siblings().children("span").text();
              if(btn == boardId)
              {
                  if(check == 0)
                  {
                      rec = Number(rec) + 1;
//                      alert(rec);
                  }
                  else
                  {
                      rec = Number(rec) - 1;
                  }
                      rec = String(rec);
                  $(this).parent().siblings().children("span").text(rec);
                  input.val(rec);
                  let count = input.val();

              $.ajax({
                         url: '/fileboard/list/recommend',
                         type: 'POST',
                         data: {
                         userId: userId,
                         boardId: boardId,
                         count: count
                         },
                         contentType: 'application/json',
                         dataType: 'json',
                         success: function(response) {
                              if(response == 1){
                                check = 1;

                              } else {
                                check = 0;
                              }
                              alert("성공");
                         },
                         error: function(xhr, status, error) {
                             console.error(error);
                             alert("실패");
                         }
                     });
               }
          });

          $("[data-delete-btn]").click(function(){
                let answer = confirm("삭제하시겠습니까?");
                const boardId =$(this).attr("data-delete-btn");
                 $.ajax({
                           url: "/fileboard/delete",
                           type: "POST",
                           cache: false,
                           data: {"id": boardId},
                           success: function(data, status, xhr){
                               if(status == "success"){
                                   if(data.status !== "OK"){
                                       alert(data.status);
                                       return;
                                   }
                               }
                           },
                       });
          });
 });

 function loadList(appId)
 {
    $.ajax({
        url: "/fileboard/list/" + appId,
        type: "GET",
        cache: false,
        success: function(data, status, xhr){
            if(status == "success"){
                //alert(xhr.responseText);   // response 결과 확인용.

                // data 매개변수 : JSON 으로 response 되면 JS object 로 변환되어 받아온다
                if(data.status !== "OK"){
                    alert(data.status);
                    return;
                }
                // ★댓글목록을 불러오고 난뒤에 삭제에 대한 이벤트 리스너를 등록해야 한다
            }
        }

    });
 }