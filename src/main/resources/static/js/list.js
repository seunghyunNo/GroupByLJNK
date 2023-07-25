$(function(){
    $("[name='pageRows']").change(function(){
     var form = $("[name='frmPageRows']");
     form.attr("method","POST");
     form.attr("action","pageRows");
     form.submit();
    });

        const userId = $("#loginUser").text();
        const appId = $("#appId").val().trim();
        let boardId = $("td").children("span").attr("data-fileboard-recommend");
        loadCount(userId,boardId);
        $("[data-recommend-btn]").click(function(){
         let currentObj = $(this);
         let btn = $(this).attr("data-recommend-btn");
         boardId = $(this).parent().siblings().children("span").attr("data-fileboard-recommend");
              if(btn == boardId)
              {
                  $.ajax({
                              url: "/recommend/check",
                              type: "POST",
                              cache: false,
                              data: {
                                "userId": userId,
                                "boardId": boardId
                              },
                              success: function(response){
                                 if(response == 1){
                                    currentObj.removeClass("bi bi-hand-thumbs-up-fill");
                                    currentObj.addClass("bi bi-hand-thumbs-up");
                                  }
                                  else
                                  {
                                       currentObj.removeClass("bi bi-hand-thumbs-up");
                                       currentObj.addClass("bi bi-hand-thumbs-up-fill");
                                  }
                                  loadCount(userId,boardId);
                              },
                          });

               }
          });

          $("[data-delete-btn]").click(function(){
                let answer = confirm("삭제하시겠습니까?");
                if(answer)
                {
                     const boardId =$(this).attr("data-delete-btn");
                     $.ajax({
                           url: "/fileboard/delete",
                           type: "POST",
                           cache: false,
                           data: {"id": boardId},
                           success: function(data, status, xhr){
                               if(status == "success"){
                                   if(data.status !== "OK"){
                                       location.reload();
                                       return;
                                   }
                               }
                           },
                       });
                }
          });

          $("[data-download-btn]").click(function(){
               const boardId = $(this).attr("data-download-btn");
                 $.ajax({
                          url: "/fileboard/list/downCount",
                          type: "POST",
                          cache: false,
                          data: {"id": boardId},
                          success: function(data, status, xhr){
                              if(status == "success"){
                                  if(data.status !== "OK"){
                                      location.reload();
                                      return;
                                  }
                              }
                          },
                      });
          });
 });

function loadCount(userId,boardId)
{

      $.ajax({
                 url: '/recommend/count',
                 type: 'GET',
                 data: {
                 "userId": userId,
                 "boardId": boardId
                 },
                 cache: false,
                 success: function(count){
                 $("[data-fileboard-recommend]").text(count);
                },
             });

}
