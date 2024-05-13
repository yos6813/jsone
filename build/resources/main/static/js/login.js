(function($){
    $(document).ready(function(){
        $('.password-box').hide();
        $('.changePw').hide();
        var timeSpan = $('.time-area.on > span'); // 시간을 표시할 요소
        var seconds = 180; // 남은 시간 변수
        var countdown;
        var countdown2;
        
        if(timeSpan.length) {
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
                    countdown2 = setTimeout(function(){
                        alert('인증번호 유효시간이 만료되었습니다.');
                        timeSpan.text = "00:00";
                        clearInterval(countdown);
                        $('.auth-input').attr('readonly', 'readonly');
                    },180000);
                },
                error : function(request, status, error) {
                    console.log(error)
                }
            });
        };

        /* 인증버튼 */
        $('.auth-btn').click(function(){
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
                        alert(result.msg);
                        clearInterval(countdown);
                        clearInterval(countdown2);
                        $('.password-box').show();
                        $('.changePw').show();
                        $('.auth-btn').hide();
                    } else {
                        alert(result.msg);
                        $('.password-box').hide();
                        $('.changePw').hide();
                    }
                },
                error : function(request, status, error) {
                    console.log(error)
                }
            });
        });
        
        /* 비밀번호 설정 */
        $('#changePw').submit(function(){
            var pw = $('.passwd').val();
            var pw2 = $('.passwd2').val();

            if(pw != pw2) {
                alert('비밀번호와 비밀번호 확인 값이 다릅니다.');
                return false;
            }
        })
    });
})(jQuery)