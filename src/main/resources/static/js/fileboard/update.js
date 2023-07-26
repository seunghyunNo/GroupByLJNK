$(function(){
    var index = 0;
    $("#btnInsert").click(function(){
        $("#files").append(`
            <div class="input-group mb-3">
            <input class="form-control col-xs-3" type="file" name="upfile${index}"/>
            <button type="button" class="btn btn-outline-danger" onclick="$(this).parent().remove()">삭제</button>
            </div>`);
        index++;
    });


    $("[file-id-delete]").click(function(){
        let fileId = $(this).attr("file-id-delete");
        deleteByIdFiles(fileId);
        $(this).parent().remove();
    });

});

function deleteByIdFiles(fileId)
{
    $("#deleteFiles").append(`<input type="hidden" name="deleteFile" value="${fileId}">`);
}