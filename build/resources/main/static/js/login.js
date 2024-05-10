(function($){
    $(document).ready(function(){
        var timeSpan = $('.time-area.on > span'); // 시간을 표시할 요소
        var seconds = 180; // 남은 시간 변수
        var countdown;
        
        if(timeSpan.length) {
            $.ajax({
                type : 'POST', // 타입 (get, post, put 등등)
                url : '/auth', // 요청할 서버url
                dataType : 'json', // 데이터 타입 (html, xml, json, text 등등)
                data : JSON.stringify({ // 보낼 데이터 (Object , String, Array)
                    "telNo" : $('input[name="loginid"]').val()
                }),
                success : function(result) { // 결과 성공 콜백함수
                    $('.id-text').val($('input[name="loginid"]').val());

                    countdown = setInterval(function(){
                        if (seconds >= 0) {
                            const minutes = Math.floor(seconds / 60);
                            const remainingSeconds = seconds % 60;
                            timeSpan.text(`${minutes.toString().padStart(2, '0')}:${remainingSeconds.toString().padStart(2, '0')}`);
                            seconds--;
                        } else {
                            alert('인증번호 유효시간이 만료되었습니다.');
                            timeSpan.text = "00:00";
                            clearInterval(countdown);
                            $('.auth-input').attr('readonly', 'readonly');
                        }
                    }, 1000);
                },
                error : function(request, status, error) { // 결과 에러 콜백함수
                    console.log(error)
                }
            });
        };
    });
})(jQuery)