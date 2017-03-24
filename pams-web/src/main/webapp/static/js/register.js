//注册表单验证  
$("#registerForm").validate({  
    errorPlacement: function(error, element) {   
        error.appendTo(element.parent());   
    },  
    rules:{  
        username:{  
            required:true,
            minlength:6, 
            maxlength:18,
            remote:{  
                url:"user/checkUser.do",//用户名重复检查，别跨域调用  
                type:"post",  
                dataType: "json",   
                data: {                     //要传递的数据  
                        username: function() {  
                            return $(".username").val();  
                     }  
              }  
            },  
        },  
        password:{  
            required:true,  
            minlength:6,   
            maxlength:18,  
        },  
        confirm_password:{  
            required:true,  
            minlength:6,  
            maxlength:18,
            equalTo:'.password'  
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
        kaptchaImage:{  
                required:true,//必填  
                remote:{  
                    url:"user/checkRandom.do",//用户名重复检查，别跨域调用  
                    type:"post",  
                    dataType: "json",   
                    data: {                     //要传递的数据  
                            username: function() {  
                                return $(".kaptchaImage").val();  
                         }  
                  }  
                },  
        }  
    },  
    //错误信息提示  
    messages:{  
        username:{  
            required:"必须填写用户名",  
            minlength:"用户名至少为6个字符",  
            maxlength:"用户名至多为18个字符",  
            remote: "用户名已存在",  
        },  
        password:{  
            required:"必须填写密码",  
            minlength:"密码至少为6个字符",  
            maxlength:"密码至多为18个字符",  
        },  
        confirm_password:{  
            required:"请再次输入密码",  
            minlength:"确认密码不能少于6个字符",  
            maxlength:"确认密码至多为18个字符",  
            equalTo: "两次输入密码不一致",
        },  
        phone:{  
            required:"请输入手机号码",  
            digits:"请输入正确的手机号码",  
        },  
        kaptchaImage:{  
            required:"请输入验证码",  
            remote: "验证码不正确",  
        },  
      
    },  
});  
//添加自定义验证规则  
jQuery.validator.addMethod("phone_number", function(value, element) {   
    var length = value.length;   
    var phone_number = /^(1[3|4|5|8][0-9]\d{8})$/   
    return this.optional(element) || (length == 11 && phone_number.test(value));   
}, "手机号码格式错误");   