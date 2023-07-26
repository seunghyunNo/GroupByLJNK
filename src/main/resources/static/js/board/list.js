$(function(){
   $("[name='pageRows']").change(function(){
    var form = $("[name='frmPageRows']");
    form.attr("method","POST");
    form.attr("action","/board/pageRows");
    form.submit();
   });
});








