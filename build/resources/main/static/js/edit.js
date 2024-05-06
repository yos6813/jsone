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
            if(confirm("삭제하시겠습니까?")) {
                alert('delete');
            }
        });
    })
})(jQuery)