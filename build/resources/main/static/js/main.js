(function($){
    $(document).ready(function(){
        /* 로그인 실패 시 상단에 뜨는 경고창 */
        $( ".alert-danger" ).animate({
            opacity: 0
        }, 3000);

        var pathname = location.pathname;
        var path = pathname.split('/');

        /* 기본 닫기 버튼 */
        $('.btn-close').click(function(){
            location.href = '/' + path[1];
        });

        /* 취소 버튼 */
        $('.close-btn').click(function(){
            location.href = '/' + path[1];
        });

        /* 로그아웃 버튼 */
        $('.btn-logout').click(function(){
            $(this).parents('form').submit();
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

        /* checkbox */
        $('.check').click(function(){
            var checkbox = $(this).prev('input[type="checkbox"]');
            
            if(checkbox.is(":checked")) {
                checkbox.prop('checked', false);
            } else {
                checkbox.prop('checked', true);
            }
        });

        /* 대시보드 경로 */
        $('.approval-box').click(function(){
            location.href = $(this).attr('id');
        });
    });
})(jQuery)