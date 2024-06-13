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
                    code: code,
                    step: $("#step").val(),
                    button: JSON.stringify({
                        "button": [
                            {
                                "name": "결재문서 확인하기",
                                "linkType": "WL",
                                "linkTypeName": "웹링크",
                                "linkMo": "http://jsoftone4.cafe24.com",
                                "linkPc": "http://전자결재.com"
                            }
                        ]
                    })
                }),
                success : function(result) {
                    location.href = document.referrer;
                },
                error : function(request, status, error) {
                    console.log(error);
                }
            })
        });

        $('.copy_id').on("click", function(){
            $('#popup-modal .popup-body').load('/popup/' + $(this).data('id'));
            $('#popup-modal').css("display", "block");
            console.log($(this).data('id'));
        });

        $('.refer_id').click(function(){
            $('#popup-modal .popup-body').load('/popup/' + $(this).data('id'));
            $('#popup-modal').css("display", "block");
        }); 

        $('.popup-close').click(function(){
            $('#popup-modal').css("display", "none");
        })

        $(document).on('click', '.file-download', function() {
            window.open($(this).attr("href"), "_blank");
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