$(function(){
    $("[name='pageRows']").change(function(){
     var form = $("[name='frmPageRows']");
     form.attr("method","POST");
     form.attr("action","pageRows");
     form.submit();
    });

        var check = 0;
        $("[data-recommend-btn]").click(function(){
         let btn = $(this).attr("data-recommend-btn");
         let span = $(this).parent().siblings().children("span").attr("data-fileboard-recommend");
         let rec = $(this).parent().siblings().children("span").text();
         alert(span);
              if(btn == span)
              {
                  if(check == 0)
                  {
                      rec = Number(rec) + 1;
                      check = 1;
                      alert(rec);
                  }
                  else
                  {
                      rec = Number(rec) - 1;
                      check = 0;

                  }
                      rec = String(rec);
                      $(this).parent().siblings().children("span").text(rec);
              }

          });

 
 });