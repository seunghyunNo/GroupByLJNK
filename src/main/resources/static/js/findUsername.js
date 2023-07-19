$(document).ready(function() {

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
           $('#email').focus();
           return;
       } else {
            // 알림창 호출
            alert("인증번호를 보냈습니다.");
       }

       // 전달할 parameter 준비
       const data = {
           "email": email
       };

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
           },
           error: function(xhr, status, error) {
                         console.error(error);
           }
       });

       $('#codeDiv').show();


   });

   $('#myButton').click(function() {
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

       const email = $("#email").val().trim();

       $.ajax({
           url: '/user/findUsername',
           type: 'POST',
           data: {email: email},
           dataType: 'text',
           success: function(response) {
               if(!response){
                    $('#username_feedback').html('아이디가 없습니다.');
                    $('#username_feedback').addClass('border border-3 p-3');
                    $('#goSignup').show();
               } else {
                    $('#username_feedback').html('아이디는 ' + response + ' 입니다.');
                    $('#username_feedback').addClass('border border-3 p-3');
                    $('#goPw').show();
                    $('#goLogin').show();
               }
           },
           error: function(xhr, status, error) {
                         console.error(error);
           }
       });

   });
});