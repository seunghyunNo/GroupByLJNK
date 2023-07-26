$(document).ready(function() {

   $('#findIdButton').click(function() {


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
       }

       $.ajax({
           url: '/user/findUsername',
           type: 'POST',
           data: {email: email},
           dataType: 'text',
           success: function(response) {

               if(!response){
                    $('#username_feedback').html('아이디가 없습니다.');
                    $('#username_feedback').addClass('border border-3 p-3');
               } else {
                    const hashResponse = hashUsername(response);
                    $('#username_feedback').html('아이디는 ' + hashResponse + ' 입니다.');
                    $('#username_feedback').addClass('border border-3 p-3');
                    $('#findFullId').show();
               }


           },
           error: function(xhr, status, error) {
                         console.error(error);
           }
       });


   });

    // 아이디 암호화
   function hashUsername(username) {

       return username.slice(0, 2) + "*".repeat(username.length - 3) + username.slice(-1);
   };

   $('#findFullId').click(function() {
        const email = $("#email").val().trim();

       // 전달할 parameter 준비
       const data = { "email": email };

       $.ajax({
                  url: '/user/findFullUsername',
                  type: 'POST',
                  data: JSON.stringify(data), // 데이터를 JSON 형식으로 변환하여 전송
                  contentType: 'application/json', // 요청의 Content-Type을 JSON으로 설정
                  dataType: 'text',
                  success: function(response) {
                      if(response === email){
                        alert("이메일을 전송하였습니다.");
                        $('#findFullId').off('click');
                        $('#findFullId').removeClass('btn-primary');
                        $('#findFullId').addClass('btn-secondary');
                      } else {
                        alert("알수없는 이유로 이메일 전송에 실패하였습니다.");
                      }
                  },
                  error: function(xhr, status, error) {
                                console.error(error);
                  }
              });

  });
});