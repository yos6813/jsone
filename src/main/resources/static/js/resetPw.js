(function($){
    $(document).ready(function(){
        /* 비밀번호 재설정 비밀번호 확인 경고 글귀 */
        $('.pw-warning').hide();
        $('.submit_btn').hide();
        $('#auth').hide();
        $('#passwd2').keyup(function(){
            var pass = $('#passwd').val();

            if(pass != $(this).val()) {
                $('.pw-warning').show();
            } else {
                $('.pw-warning').hide();
            }
        });

        /* 인증버튼 클릭 시 인증 진행 */
        $('.auth-btn').click(function(){
            if($('#loginid').val() == '') {
                alert("전화번호를 입력해주세요.");
            } else {
                var timeSpan = $('.time-area > span'); // 시간을 표시할 요소
                var seconds = 180; // 남은 시간 변수
                var countdown;
                
                if(timeSpan.length) {
                    $('#auth').show();
                    countdown = setInterval(function(){
                        if (seconds >= 0) {
                            const minutes = Math.floor(seconds / 60);
                            const remainingSeconds = seconds % 60;
                            timeSpan.text(`${minutes.toString().padStart(2, '0')}:${remainingSeconds.toString().padStart(2, '0')}`);
                            seconds--;
                        }
                    }, 1000);

                    $.ajax({
                        type : 'POST',
                        url : '/sendAuth',
                        dataType : 'json',
                        contentType: 'application/json',
                        data : JSON.stringify({
                            "telNo" : $('input[name="loginid"]').val()
                        }),
                        success : function(result) {
                            $('.id-text').val($('input[name="loginid"]').val());
                            setTimeout(function(){
                                alert('인증번호 유효시간이 만료되었습니다.');
                                timeSpan.text = "00:00";
                                clearInterval(countdown);
                                $('.auth-input').attr('readonly', 'readonly');
                            },180000);

                            $('.submit_btn').show();
                            $('.auth-btn').hide();
                        },
                        error : function(request, status, error) {
                            console.log(error)
                        }
                    });
                };
            }
        });

        $('.submit_btn').click(function(){
            var authNum = $('.authNum').val();

            $.ajax({
                type : 'POST',
                url : '/authCheck',
                dataType : 'json',
                contentType: 'application/json',
                data : JSON.stringify({
                    "authNum" : authNum
                }),
                success : function(result) {
                    if(result.status == 'success') {
                        $('#resetForm').submit();
                    } else {
                        alert(result.msg);
                    }
                },
                error : function(request, status, error) {
                    console.log(error)
                }
            });
        })
    });
})(jQuery)