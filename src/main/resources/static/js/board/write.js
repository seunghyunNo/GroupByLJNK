$(function(){
    var index = 0;
    $("#btnInsert").click(function(){
        $("#files").append(`
            <div class="input-group mb-3">
            <input class="form-control col-xs-3" type="file" name="upload${index}"/>
            <button type="button" class="btn btn-outline-danger" onclick="$(this).parent().remove()">삭제</button>
            </div>`);
        index++;

    })


    $("#content").summernote({
        height:300
    });

});