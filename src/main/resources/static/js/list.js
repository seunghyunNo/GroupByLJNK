$(function(){
   $("[name='pageRows']").change(function(){
    var form = $("[name='frmPageRows']");
    form.attr("method","POST");
    form.attr("action","pageRows");
    form.submit();
   });
    var check = 0;

    $("#btnRec").click(function(){
        let rec = $("#rec").val();
        if(check == 0)
        {
            check = 1;
            rec = Number(rec) + 1;

        }
        else
        {
            check = 0;
            rec = Number(rec) - 1;

        }

        rec = String(rec);
        $("#rec").text(rec);
        $("#rec").val(rec);

        alert(rec);
    });

});