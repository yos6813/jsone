(function($){
    $(document).ready(function(){
        $( ".alert-danger" ).animate({
            opacity: 0
        }, 3000);

        $('.btn-close').click(function(){
            history.back();
        });

        $('.close-btn').click(function(){
            history.back();
        });

        $('.btn-logout').click(function(){
            $(this).parents('form').submit();
        });

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