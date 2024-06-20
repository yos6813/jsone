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
                        '<div class="file-box upload">' +
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
                    $('.body-bottom .upload').remove();
                    
                    $('.body-bottom').append(
                        '<div class="file-box">' +
                            '<span class="filename">' + result.oriFileName + '</span>' +
                            '<input type="hidden" name="original_file_name[]" value="' + result.oriFileName + '" />' +
                            '<input type="hidden" name="temp_file_name[]" class="temp_file_name" value="' + result.fileName + '" />' +
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
            var fileName = that.prevAll('.temp_file_name').val();
            var attachid = that.prevAll('.attachid').val();
            var name = that.prevAll('.filename').text();
            console.log(attachid);
            if(confirm(name + " 파일을 삭제하시겠습니까?")) {
                $.ajax({
                    type: 'POST',
                    url: '/deleteFile',
                    data: {
                        fileName: fileName,
                        attachid: attachid
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

            if($('input[name="approver[]"]:checked').length) {
                if($(".form_type").val() == 'update') {
                    $('#update').submit();
                }
            } else {
                alert('결재자를 선택해주세요.');
            }

            
        });

        /* 결재올리기 */
        $('.approval-btn').click(function(){
            $(".form_type").val('approval');
            var telNo = new Array();

            if($('#type_cd').val() != '999') {
                if($('input[name="approver[]"]:checked').length) {
                    $('input[name="approver[]"]:checked').each(function(index) {
                        telNo[index] = $(this).data("id");
                    });

                    $.ajax({
                        type: 'POST',
                        url: '/sendApproval',
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        data: JSON.stringify({
                            id: $('.id').val(),
                            name: $('.emp_nm').val(),
                            title: $('.title').val(),
                            telNo: telNo.toString(),
                            button: JSON.stringify({
                                "button": [
                                    {
                                        "name": "결재문서 확인하기",
                                        "linkType": "WL",
                                        "linkTypeName": "웹링크",
                                        "linkMo": "http://m.jsoftone.com",
                                        "linkPc": "http://jsoftone.com"
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
                } else {
                    alert('결재자를 선택해주세요.');
                }
            } else {
                alert('문서종류를 선택해주세요.');
            }
        });
    })
})(jQuery)