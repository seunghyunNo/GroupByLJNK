$(function(){
    $("[name='pageRows']").change(function(){
     var form = $("[name='frmPageRows']");
     form.attr("method","POST");
     form.attr("action","pageRows");
     form.submit();
    });
        let userId = $("#loginUser").text();

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
                let delForm = $(this).parent().siblings("form");
                let answer = confirm("삭제하시겠습니까?");
                if(answer){
                    delForm.submit();
                }
          });

 
 });