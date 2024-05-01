(function($){
    $(document).ready(function(){
        $('.btn-close').click(function(){
            history.back();
        });

        $('.close-btn').click(function(){
            history.back();
        });

        $('.btn-logout').click(function(){
            $(this).parents('form').submit();
        })

        $('.personal-nav').hide();
        $('.sub-title').click(function(){
            $('.personal-nav').slideToggle();
        });

        $('.approval-box').click(function(){
            location.href = $(this).attr('id');
        });

        $('.sign-bottom span').click(function(){

        });
    });
})(jQuery)