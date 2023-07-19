$(function(){
   $("[name='pageRows']").change(function(){
    var form = $("[name='frmPageRows']");
    form.attr("method","POST");
    form.attr("action","pageRows");
    form.submit();
   });

   let count = 0;
   var recommend = false;
   $("#btnRec").click(function(){

      if(!recommend)
      {
        recommend = true;
        count++;
        var cnt = $("#recommendCnt").val() + count;
        $("#recommendCnt").text(cnt);
      }
      else
      {
        recommend = false;
        count--;
        var cnt = $("#recommendCnt").val() + count;
        $("#recommendCnt").text(cnt);
      }
   });

});