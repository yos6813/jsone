(function($) {
    $(document).ready(function(){
        $('.btn-box button').click(function(){
            var code = 0;

            if($(this).attr('id') != 'viewerCheck') {
                code = $('#pos_cd').val();
            } else {
                code = $("#coop_cd").val();
            }
            $.ajax({
                type : 'POST',
                url : '/' + $(this).attr('id'),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data : JSON.stringify({
                    docid: $('#docid').val(),
                    empid: $('#empid').val(),
                    code: code
                }),
                success : function(result) {
                    location.href = document.referrer;
                },
                error : function(request, status, error) { 
                    console.log(error);
                }
            })
        });

        $('.copy_id').click(function(){
            var href = $(this).data('href');
            location.href = location.origin + "/" + href;
        });

        $('.refer_id').click(function(){
            var href = $(this).data('href');
            location.href = location.origin + "/" + href;
        });

        /* 뷰 하단 대화 전송 시 AJAX 처리 */
        $('#saveChat_submit').click(function(){
            $.ajax({
                type : 'POST',
                url : '/saveChat',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data : JSON.stringify({
                    docid: $('#docid').val(),
                    empid: $('#empid').val(),
                    chatContents: $('#chatContents').val()
                }),
                success : function(result) { 
                    $('#chatContents').val("");
                    $(".comment-list").append(
                        '<div class="comment bg-yellow right">' +
                            '<div class="chat-user">' +
                                '<span>' + result.emp_nm + '</span>' +
                            '</div>' +
                            '<div class="chat-contents">' +
                                '<span>' + result.chatContents + '</span>' +
                            '</div>' +
                        '</div>'
                    );
                },
                error : function(request, status, error) { 
                    console.log(error);
                }
            })
        });
    });
})(jQuery)