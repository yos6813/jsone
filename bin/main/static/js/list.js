(function($){
    $('document').ready(function(){
        $('.sign-bottom span').hide();
        $(window).scroll(function(){
            if($(this).scrollTop() != 0) {
                $('.sign-bottom span').show();
            }
        });

        $('.sign-bottom span').click(function(){
            $(window).scrollTop(0);
        })
    });
})(jQuery)