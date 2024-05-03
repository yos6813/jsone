(function($){
    $(document).ready(function(){
        /* 로그인 실패 시 상단에 뜨는 경고창 */
        $( ".alert-danger" ).animate({
            opacity: 0
        }, 3000);

        /* 기본 닫기 버튼 */
        $('.btn-close').click(function(){
            history.back();
        });

        /* 취소 버튼 */
        $('.close-btn').click(function(){
            history.back();
        });

        /* 로그아웃 버튼 */
        $('.btn-logout').click(function(){
            $(this).parents('form').submit();
        });

        /* 비밀번호 재설정 비밀번호 확인 경고 글귀 */
        $('.pw-warning').hide();
        $('#passwd2').keyup(function(){
            var pass = $('#passwd').val();

            if(pass != $(this).val()) {
                $('.pw-warning').show();
                $('.submit_btn').attr('disabled', 'disabled');
            } else {
                $('.pw-warning').hide();
                $('.submit_btn').removeAttr('disabled');
            }
        });

        /* 뷰 & 편집 페이지 결재, 공람 토글 */
        $('.approval-toggle').hide();
        $('.announcement-toggle').hide();
        $('.approval').click(function(){
            $('.approval-toggle').slideToggle();
        });

        $('.announcement').click(function(){
            $('.announcement-toggle').slideToggle();
        })

        /* 대시보드 경로 */
        $('.approval-box').click(function(){
            location.href = $(this).attr('id');
        });
    });
})(jQuery)