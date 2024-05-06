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
        $(document).on('click', '.list', function(){
            location.href = "/" + $(this).data('href');
        });

        /* 리스트가 하단으로 내려갈 때 상단으로 가기 버튼 */
        $('.sign-bottom span').click(function(){
            $(window).scrollTop(0);
        });

        /* 더보기 이벤트 */
        $('.more-btn').click(function(){
            $.ajax({
                type : 'POST',
                url : '/pagination',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data : JSON.stringify({
                    type_cd: $('#type_cd').val(),
                    page: $('#page').val(),
                }),
                success : function(result) {
                    $('#page').val(parseInt($('#page').val()) + 20);
                    if(parseInt($('#page').val()) >= parseInt($('#full_cnt').val())) {
                        $('.more-btn').hide();
                    }

                    for(var i = 0; i<result.length; i++) {
                        var icon = '';
                        var href = '';
                        if(result[i].cnt > 0) {
                            icon = '<i class="fa-regular fa-file-lines"></i>';
                        }

                        if(result[i].type_cd == '임시저장') {
                            href = 'edit/' + result[i].docid;
                        } else {
                            href = result[i].docid;
                        }

                        $(".list-box").append(
                            '<div class="list" data-href="' + href + '">' +
                            '<span>' + result[i].title + '</span>' + icon +
                            '<span class="second-line">' + result[i].create_date + ' / ' + result[i].type_cd + ' / ' + result[i].status_cd + ' / ' + result[i].emp_nm + ' / ' + result[i].name + '</span>' +
                            '<span>' + result[i].contents_text + '</span>'
                        );
                    }
                },
                error : function(request, status, error) {
                    console.log(error);
                }
            })
        });
    });
})(jQuery)