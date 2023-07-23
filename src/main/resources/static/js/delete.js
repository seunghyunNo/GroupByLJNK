$(function(){
    // 회원 탈퇴 이벤트
    $("#button").click(function(){
        let answer = confirm("탈퇴하시겠습니까?");
        if(answer){
            $("form[name='frmDelete']").submit();
        }
    });
});