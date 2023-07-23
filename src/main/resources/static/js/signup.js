$(document).ready(function() {

    // 아이디 검사
    $('#username').on('keyup', function(){
        let username = $('#username').val();

        // validation 으로 처리한 error message 초기화
        if(username){
            $('#error').html("");
        }

        // 아이디 글자수 확인
        if(username.length < 5){
            $('#username_feedback').html('아이디는 5글자 이상이어야 합니다');
            $('#username_feedback').css('color', '#f00');
            return;
        }

        // 아이디 중복 확인
        $.ajax({
            url: '/user/signup/nameCheck',
            type: 'POST',
            data: {username: username},
            dataType: 'json',
            success: function(response) {
                if(response == 1){
                    $('#username_feedback').html('이미 존재하는 아이디 입니다.');
                    $('#username_feedback').css('color', '#f00');
                } else {
                    $('#username_feedback').html('사용할 수 있는 아이디 입니다');
                    $('#username_feedback').css('color', '#00f');
                }
            },
            error: function(xhr, status, error) {
               console.error(error);
            }
        });
    });



     // 비밀번호, 비밀번호 확인 같은지 비교
    $('#re_password').on('keyup', function() {
        let password = $('#password').val();
        let re_password = $('#re_password').val();

        if (password !== re_password) {
            $('#password_feedback').html('비밀번호가 일치하지 않습니다.');
            $('#password_feedback').css('color', '#f00');
        } else {
            $('#password_feedback').html('');
        }
    });

    // 비밀번호 확인란이 작성된 상태에서 비밀번호란을 고칠경우
    $('#password').on('keyup', function(){
         let password = $('#password').val();
         let re_password = $('#re_password').val();

         if(re_password){
             if(password !== re_password){
                $('#password_feedback').html('비밀번호가 일치하지 않습니다.');
                $('#password_feedback').css('color', '#f00');
             } else {
                $('#password_feedback').html('');
             }
         }
    });

   $('#authButton').click(function() {


       // 입력한 메일
       const email = $("#email").val().trim();

       // 검증
       var emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
       if (!email) {
           alert("이메일을 입력해주세요.");
           $("#email").focus();
           return;
       } else if (!emailRegex.test(email)) {
           alert('유효한 이메일 주소를 입력하세요.');
           emailInput.focus();
           return;
       }

       // 메일 중복 확인
       $.ajax({
            url: '/user/signup/mailCheck',
            type: 'POST',
            data: {email: email},
            dataType: 'json',
            success: function(response) {
               if(response == 1){
                   $('#code_feedback').html('이미 존재하는 이메일 입니다.');
                   $('#code_feedback').css('color', '#f00');
                   $("#email").focus();
                   return;
               } else{

                   // 알림창 호출
                   alert("인증번호를 보냈습니다.");
                   $('#code_feedback').html('');

                   // 전달할 parameter 준비
                   const data = { "email": email };

                   // 메일 검증
                   $.ajax({
                       url: '/signup/authEmail',
                       type: 'POST',
                       data: JSON.stringify(data), // 데이터를 JSON 형식으로 변환하여 전송
                       contentType: 'application/json', // 요청의 Content-Type을 JSON으로 설정
                       dataType: 'text',
                       success: function(response) {
                           var inputElement = $('#authCodeInput');
                           inputElement.val(response);
                            // 'myButton' 클릭 이벤트를 success 되어 값이 전송이 되었을떄 활성화
                            $('#myButton').unbind('click').click(myButtonClickHandler);
                       },
                       error: function(xhr, status, error) {
                                     console.error(error);
                       }
                   });

                   $('#codeDiv').show();

               }
          },
          error: function(xhr, status, error) {
              console.error(error);
          }
      });
   });

   // 'myButton' 클릭 이벤트 함수
   function myButtonClickHandler() {
       var authCodeInput = $('#authCodeInput').val(); // 인증번호 입력 필드의 값 가져오기
       var code = $('#code').val(); // 확인 버튼을 클릭할 때 입력한 인증번호 가져오기

       if (authCodeInput === code) {
           $('#codeDiv').hide();
           $('#email').prop('readonly', true);
           $('#code_feedback').html('인증에 성공하셨습니다.');
           $('#code_feedback').css('color', '#00f');
       } else {
           $('#code_feedback').html('인증번호가 맞지 않습니다.');
           $('#code_feedback').css('color', '#f00');
       }
   }

   // 폼 제출 이벤트
    $('form').submit(function(event) {
       // 이벤트 기본 동작 막기
       event.preventDefault();

       // 입력한 메일
       var authCodeInput = $('#authCodeInput').val(); // 인증번호 입력 필드의 값 가져오기
       var code = $('#code').val(); // 확인 버튼을 클릭할 때 입력한 인증번호 가져오기

       //
       if(!code || authCodeInput !== code){
            $('#code_feedback').html('이메일 인증이 안되었습니다');
            $('#code_feedback').css('color', '#f00');
            return;
       }

       // 유효성 검사를 통과한 경우 폼 제출
       $(this).unbind('submit').submit();
     });
});
