$(function() {  
        $('#kaptchaImage').click(function() {
        	$(this).attr('src', path + '/background/code/kaptcha-img?#' + Math.floor(Math.random() * 100));});  
    });  
    
    (function($){
        $(document).ready(function(){
            $(".submitBtn").click(function() {
                var obj = $(this);
                $.ajax({
                    url: path + '/background/code/verification',
                    type:'POST',
                    data:{
                    	kaptcha: function() {
                    		return $.trim($("input[name=kaptcha]").val());   //去掉首尾空格
                    	}
                    }, 
                    dataType:'json',
                    async:false,
                    success:function(result) {
                        if(result.status == 0) {
                            obj.parents('form').submit();
                        }else{
                            $("#kaptchaImage").click();
                            $(".kaptcha-code").val("");
                            $(".tips").html(result.message)
                            setTimeout(function(){
                                $(".tips").empty();
                            },2000);
                        }
                    },
                    error:function(msg){
                        $(".tips").html('Error:'+msg.toSource());
                    }
                })
                return false;
            })
        });
    })(jQuery);