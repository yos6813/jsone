(function($){
    $(document).ready(function(){
        $('.btn-close').click(function(){
            history.back();
        });

        $('.second-nav').hide();
        $('.sec-nav').click(function(){
            $('.second-nav').slideToggle();
        });
    });
})(jQuery)