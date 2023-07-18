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


    $("[fileid-delete]").click(function(){
        let fileId = $(this).attr("fileid-delete");
        deleteByIdFiles(fileId);
        $(this).parent().remove();
    });

    $("#content").summernote({
        height:100
    });
});

function deleteByIdFiles(fileId)
{
    $("#deleteFiles").append(`<input type="hidden" name="deleteFile" value=${fileId}>`);
}