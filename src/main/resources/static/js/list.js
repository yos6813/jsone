(function($){
    $('document').ready(function(){
        /* 리스트가 하단으로 내려갈 때 상단으로 가기 버튼 visible 여부 */
        $('.sign-bottom span').hide();
        $(window).scroll(function(){
            if($(this).scrollTop() != 0) {
                $('.sign-bottom span').show();
            } else {
                $('.sign-bottom span').hide();
            }
        });

        /* 각 리스트 페이지 nav 토글 */
        $('.sub-nav').hide();
        $('.sub-title').click(function(){
            $('.sub-nav').slideToggle();
        });

        /* 리스트 항목 선택 시 뷰 or 편집 페이지로 이동 */
        $('.list').click(function(){
            location.href = "/" + $(this).data('href');
        });

        /* 리스트가 하단으로 내려갈 때 상단으로 가기 버튼 */
        $('.sign-bottom span').click(function(){
            $(window).scrollTop(0);
        });

    });
})(jQuery)