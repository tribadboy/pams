//点击验证码更换图片
$(function() {  
    $('#kaptchaImage').click(function() {
    	$(this).attr('src',path + '/web/code/kaptcha-img?#' + Math.floor(Math.random() * 100));});  
}); 
//注册表单验证 
$("#registerForm").validate({  
    errorPlacement: function(error, element) {   //把错误信息放在验证的元素后面;   
    	error.appendTo( element.parent());
    },  
    onfocusout: function(element) { $(element).valid(); },
    onkeyup: false,
    onclick: false,
    rules:{  
        username:{  
            required:true,
            minlength:1, 
            maxlength:18,
            remote: {
                type:"POST",  
                url: path + "/web/user/checkUsername",                                              
                data:{
                	username: function() {
                		return $("#username").val();
                	}
                },  
                dataType:'json',
                dataFilter: function(data,type) {  
                	data = eval('('+data+')');
                    if (data.status == 0)  
                        return true;  
                    else  
                        return false;  
               }  
            },
        },  
        password:{  
            required:true,  
            minlength:1,   
            maxlength:18,  
        },  
        confirm_password:{  
            required:true,  
            minlength:1,  
            maxlength:18,
            equalTo:'.password',
        },  
        email:{  
            required:true,  
            email:true,
        },  
        phone:{  
            required:true,  
            phone_number:true,//自定义的规则  
            digits:true,//整数  
        },  
        kaptcha:{  
            required:true,
            remote: {
                type:"POST",  
                url: path + "/web/code/verification",                                              
                data:{
                	kaptcha: function() {
                		return $("#kaptcha").val();
                	}
                },  
                dataType:'json',
                dataFilter: function(data,type) {  
                	data = eval('('+data+')');
                    if (data.status == 0)  
                        return true;  
                    else {
                        $("#kaptchaImage").click();
                        $(".kaptcha").val("");
                    	return false;
                    }
               }  
            },           
        }  
    },  
    //错误信息提示  
    messages:{  
        username:{  
            required:"用户名必填",  
            minlength:"用户名至少1个字符",  
            maxlength:"用户名至多18个字符",  
            remote:"该用户名已被注册",
        },  
        password:{  
            required:"密码必填",  
            minlength:"密码至少1个字符",  
            maxlength:"密码至多18个字符",  
        },  
        confirm_password:{  
            required:"请再次输入密码",  
            minlength:"确认密码不能少于1个字符",  
            maxlength:"确认密码至多为18个字符",  
            equalTo: "两次输入密码不一致",
        },  
        email:{
            required:"邮箱必填",
            email:"邮箱格式不正确",
        },
        phone:{  
            required:"请输入手机号码",  
            digits:"手机号码格式不正确",  
        },  
        kaptcha:{  
            required:"请输入验证码",
            remote:"验证码输入错误",
        },  
          
    },  
});  
//用户信息修改验证
$("#updateUserInfoForm").validate({  
    errorPlacement: function(error, element) {   //把错误信息放在验证的元素后面
        error.appendTo(element.parent());   
    },  
    onfocusout: function(element){		//	仅在失去焦点时做验证
        $(element).valid();
    },
    onkeyup: false,
    onclick: false,
    rules:{  
        password:{  
            required:true,  
            minlength:1,   
            maxlength:18,  
        },  
        confirm_password:{  
            required:true,  
            minlength:1,  
            maxlength:18,
            equalTo:'.password',
        },  
        email:{  
            required:true,  
            email:true,
        },  
        phone:{  
            required:true,  
            phone_number:true,//自定义的规则  
            digits:true,//整数  
        }, 
        
    },
    //错误信息提示  
    messages:{  
        password:{  
            required:"密码必填",  
            minlength:"密码至少1个字符",  
            maxlength:"密码至多18个字符",  
        },  
        confirm_password:{  
            required:"请再次输入密码",  
            minlength:"确认密码不能少于1个字符",  
            maxlength:"确认密码至多为18个字符",  
            equalTo: "两次输入密码不一致",
        },  
        email:{
            required:"邮箱必填",
            email:"邮箱格式不正确",
        },
        phone:{  
            required:"请输入手机号码",  
            digits:"手机号码格式不正确",  
        },  
    },  
});  
//添加自定义验证规则  
jQuery.validator.addMethod("phone_number", function(value, element) {   
    var length = value.length;   
    var phone_number = /^(1[3|4|5|8][0-9]\d{8})$/ ;  
    return this.optional(element) || (length == 11 && phone_number.test(value));   
}, "手机号码格式错误");  