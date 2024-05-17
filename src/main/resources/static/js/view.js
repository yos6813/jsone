(function($) {
    $(document).ready(function(){
        $('.btn-box button').click(function(){
            $.ajax({
                type : 'POST',
                url : '/' + $(this).attr('id'),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data : JSON.stringify({
                    docid: $('#docid').val(),
                    empid: $('#empid').val(),
                    code: $('#code').val()
                }),
                success : function(result) {
                    location.href = document.referrer;
                },
                error : function(request, status, error) { 
                    console.log(error);
                }
            })
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