(function($){
    $(document).ready(function(){
        $('.btn-close').click(function(){
            history.back();
        });

        $('.login_btn').click(function(){
            location.href = "/dashboard";
        })

        $('.personal-nav').hide();
        $('.sub-title').click(function(){
            $('.personal-nav').slideToggle();
        });

        $('.approval-box').click(function(){
            location.href = $(this).attr('id');
        })
    });
})(jQuery)