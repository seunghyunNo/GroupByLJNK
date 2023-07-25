$(function(){
   $("[name='pageRows']").change(function(){
    var form = $("[name='frmPageRows']");
    form.attr("method","POST");
    form.attr("action","pageRows");
    form.submit();
   });
});

//function(){
//
//    let recommendationCount =0;
//
//    document.getElementById("btnRec").addEventListener("click",function(){
//        recommendationCount++;
//
//        alert("성공");
//    })
//
//
//}






