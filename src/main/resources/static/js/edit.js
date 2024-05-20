(function($){
    $(document).ready(function(){
        /* 내용 편집기  - https://summernote.org/ */
        $('#content').summernote({
            tabsize: 2,
            height: 120,
            toolbar: [
            ['style', ['style']],
            ['font', ['bold', 'underline', 'clear']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['table', ['table']],
            ['insert', ['link', 'picture', 'video']],
            ['view', ['fullscreen', 'codeview', 'help']]
            ]
        });

        /* 삭제버튼 */
        $('.delete-btn').click(function(){
            $(".form_type").val('delete');
            if(confirm("삭제하시겠습니까?")) {
                $('#update').submit();
            }
        });

        $(".file-upload").click(function(){
            $("#file-upload").click();
        });

        $("#file-upload").change(function(){
            var formData = new FormData();
            formData.append('file', $(this)[0].files[0]);

            $.ajax({
                url: '/fileUpload',
                data : formData,
                type : 'POST',
                enctype:'multipart/form-data',
                contentType : false,
                processData: false,
                xhr: function() {
                    $('.body-bottom').append(
                        '<div class="file-box">' +
                            '<span class="filename">1 file uploaded</span>' +
                            '<span class="minus-icon">' +
                                '<i class="fa-solid fa-circle-minus"></i>' +
                                '<span class="file-size"></span>' +
                            '</span>' +
                            '<div class="file-bottom">' +
                                '<div class="progress">' +
                                    '<div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>' +
                                '</div>' +
                            '</div>' +
                        '</div>'
                    );

                    //XMLHttpRequest 재정의 가능
                    var xhr = $.ajaxSettings.xhr();
                    xhr.upload.onprogress = function(e) {
                        //progress 이벤트 리스너 추가
                        var percent = e.loaded  * 100 / e.total;
                        $('.progress-bar').val(percent);
                    };

                    return xhr;
                },  success : function(result) {
                    $('.body-bottom .file-box:last-child').remove();

                    $('.body-bottom').append(
                        '<div class="file-box">' +
                            '<span class="filename">' + result.fileName + '</span>' +
                            '<input type="hidden" name="original_file_name[]" value="' + result.fileName + '" />' +
                            '<span class="minus-icon">' +
                                '<i class="fa-solid fa-circle-minus"></i>' +
                                '<span class="file-size"> ' + Math.round(result.size) + 'MB</span>' +
                            '</span>' +
                            '<div class="file-bottom">' +
                                '<span class="green-txt">Uploaded</span>' +
                            '</div>' +
                        '</div>'
                    );
                }});
        });

        $(document).on('click', '.minus-icon', function(){
            var that = $(this);
            var fileName = that.prev('.filename').text();
            if(confirm(fileName + " 파일을 삭제하시겠습니까?")) {
                $.ajax({
                    type: 'POST',
                    url: '/deleteFile',
                    data: {
                        fileName: fileName
                    },
                    success: function(response) {
                        if(response.status == 'success') {
                            that.parents('.file-box').remove();
                        } else {
                            alert(response.msg);
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error('파일 삭제 실패:', error);
                    }
                });
            }
        });

        /* 글 저장 */
        $('.save-btn').click(function(){
            $(".form_type").val('update');

            if($(".form_type").val() == 'update') {
                $('#update').submit();
            }
        });

        /* 결재올리기 */
        $('.approval-btn').click(function(){
            $(".form_type").val('approval');

            $.ajax({
                type: 'POST',
                url: '/sendApproval',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({
                    name: $('.emp_nm').val(),
                    title: $('.title').val(),
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
                success: function(response) {
                    $('#update').submit();
                },
                error: function(xhr, status, error) {
                    console.log(error);
                }
            });
        });
    })
})(jQuery)