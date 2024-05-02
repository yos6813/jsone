(function($) {
    $(document).ready(function(){
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

                        console.log(result);
                    },
                    error : function(request, status, error) { 
                        console.log(error);
                    }
                })
            });
    });
})(jQuery)