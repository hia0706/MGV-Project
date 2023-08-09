
		
/**
 * 좋아요 버튼 관련 코드
 */
$(function() {
		let likeButtonClicked = false;
		$("#like-button").click(function(event) {
			event.preventDefault();
			if (!likeButtonClicked) {
				$(this).removeClass("btn-outline-secondary").addClass("btn-secondary")
					   .text("좋아요♥");
				likeButtonClicked = true;
			} else {
				$(this).removeClass("btn-secondary").addClass("btn-outline-secondary")
						.text("좋아요♡");
				likeButtonClicked = false;
			}
		});

/*
	신청버튼 관련 코드
*/	
		let joinButtonClicked = false;
		$("#join-button").click(function(event) {
			event.preventDefault();
			if (!joinButtonClicked) {
				$(this).removeClass("btn-outline-secondary").addClass("btn-secondary")
					   .text("취소하기");
				joinButtonClicked = true;
			} else {
				$(this).removeClass("btn-secondary").addClass("btn-outline-secondary")
						.text("신청하기");
				joinButtonClicked = false;
			}
		});

       	let no = document.querySelector("input[name=no]").value;
		let sort = document.querySelector("input[name=sort]").value;
		let rows = document.querySelector("input[name=rows]").value;
		let opt = document.querySelector("input[name=opt]").value;
		let keyword = document.querySelector("input[name=keyword]").value;
		let page = document.querySelector("input[name=page]").value;
		let loginId = $("#login-id").attr("value");
		let commentNo = $(this).attr("data-cancel-comment");

    // 답글 작성 버튼을 클릭하면
    $(".commentUserInfo").on("click", "[data-comment-no]", function (event) {
        event.preventDefault();

        let commentNo = $(this).attr("data-comment-no");

        // 이미 답글 작성 폼이 열려있는지 확인하고, 열려있으면 닫기
        if ($("#reply-form-" + commentNo).length > 0) {
            $("#reply-form-" + commentNo).remove();
            $("#btn-a-re-reply-" + commentNo).attr('id', 'btn-a-reply-' + commentNo).text('답글쓰기');
            return;
        }

        // 답글 작성 폼 HTML
        let content = `
            <div class="col-11 ms-5 CommentWriter" id="reply-form-${commentNo}">
                <div class="comment-inbox border p-2 rounded">
                    <em class="comment_inbox_name">${loginId}</em>
                    <form id="form-comment" method="post" action="/board/movie/addComment">
                        <input type="hidden" name="no" value="${no}" />
                        <input type="hidden" name="parentNo" value="${commentNo}" />
                        <input type="hidden" name="greatNo" value="${commentNo}" />
						<input type="hidden" name="id" value=${loginId} />
						<input type="hidden" name="sort" value=${sort} />
						<input type="hidden" name="rows" value=${rows} />
						<input type="hidden" name="page" value=${page} />
						<input type="hidden" name="opt" value=${opt} />
						<input type="hidden" name="keyword" value=${keyword} />
                        <div class="row">
                            <div id="content-box">
                                <textarea rows="2" class="comment_inbox_text" name="content" id="content"
                                          style="border: none; overflow: hidden; overflow-wrap: break-word;"
                                          placeholder="댓글을 남겨보세요"></textarea>
                            </div>
                            	<div class="register_box">
	                                <button class="btn btn-outline-white btn-sm float-end" id="btn-comment"
	                                        style="border: none" type="submit">등록</button>
	                            </div>
	                        </div>
	                    </form>
	                </div>
	            </div>`;
	
	        // 답글 작성 폼 추가
	        $("#reply-comment-box-" + commentNo).append(content);
	        // 답글 쓰기 버튼을 취소 버튼으로 변경
	        $("#btn-a-reply-" + commentNo).attr('id', 'btn-a-re-reply-' + commentNo).text('취소');
	    });
	
	    // 답글 작성 취소 버튼을 클릭하면 폼을 제거하고 버튼을 답글쓰기로 변경
	    $(".commentUserInfo").on("click", "[data-cancel-comment]", function (event) {
	        event.preventDefault();
	
	
	        // 답글 작성 폼을 제거하고 버튼을 답글쓰기로 변경
	        $("#reply-form-" + commentNo).remove();
	        $("#btn-a-re-reply-" + commentNo).attr('id', 'btn-a-reply-' + commentNo).text('답글쓰기');
	    });
		

		
		

		$(".re-commentUserInfo").on("click", "#a-re-reply", function(event) {
		event.preventDefault();		
			/*
				<input type="hidden" name="parentId" value=부모 />
				<input type="hidden" name="greatId" value=부모의 부모(최고조상) />
			 */		
			let content = `
		   	<div class="col-11 ms-5 CommentWriter">
				<div class="comment-inbox border p-2 rounded"> 
	
					<em class="comment_inbox_name">${loginId}</em>
					
					<form id="form-comment" method="post" action="/board/movie/addComment" >
						<input type="hidden" name="no" value="${no}" />
						<input type="hidden" name="id" value=${loginId} />
						<input type="hidden" name="sort" value=${sort} />
						<input type="hidden" name="rows" value=${rows} />
						<input type="hidden" name="page" value=${page} />
						<input type="hidden" name="opt" value=${opt} />
						<input type="hidden" name="keyword" value=${keyword} />
		 				<div class="row">
							<div id="content-box " >
								<textarea rows="2" class="comment_inbox_text" id="content" name="content" style="border: none; overflow: hidden; overflow-wrap: break-word; " 
								placeholder="댓글을 남겨보세요"></textarea>
							</div>
							<div class="register_box">
								<button class="btn btn-outline-white btn-sm float-end" id="btn-comment" style="border: none" type="submit">등록</button>
							</div>
						</div>
					</form>   	
				</div>
	   		</div>  			
			`;

			$("#re-reply-comment-box").append(content)
			$("#a-re-reply").attr('id', 're-reply').text('취소')
			
		});
		
		$(".re-commentUserInfo").on("click", "#re-reply", function(event) {
			event.preventDefault();	
			
			$("#re-reply-comment-box").empty()
			$("#re-reply").attr('id', 'a-re-reply').text('답글쓰기')
		
		});
		
		$("#btn-comment").on("click", function() {
			let content = $("#content-box textarea").val();
			
		    if(content === "") { // 댓글 내용이 비어있을 때
		        Swal.fire({
		            icon: 'error',
		            text: '댓글 내용을 작성해주세요.',
		        });
		    } else { // 댓글 내용이 비어있지 않을 때
		        $("#form-comment").submit(); // 폼 제출
		    }
			
			
		});

	
})



		