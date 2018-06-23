$(function(){

	/**
	 * 正则表达式校验
	 */
	jQuery.validator.addMethod("regex", //addMethod第1个参数:方法名称
		function(value, element, params) { //addMethod第2个参数:验证方法，参数（被验证元素的值，被验证元素，参数）
			if (value == null || value == '') {//为空的话不进行验证，返回成功
				return true;
			}
			var exp = new RegExp(params); //实例化正则对象，参数为传入的正则表达式
			return exp.test(value); //测试是否匹配
		}, UDP.Public.getmessage("validate.regex","格式错误!")); //addMethod第3个参数:默认错误信息

	/**
	 * 纯空格校验
	 */
	jQuery.validator.addMethod("spaceCheck", function(value, element, params) {
		var compName = $("#" + params).val();
		if (compName.trim() == '') {
			return false;
		}
		return true;
	}, UDP.Public.getmessage("validate.spaceCheck","不能为空格!"));

	/**
	 * 手机号验证
	 * */
	jQuery.validator.addMethod("phone", function(value,element,paramsvalue) {
		var re = /(^0\d{2,3}\-\d{7,8}$)|(^1[3|4|5|6|7|8][0-9]{9}$)/g;
		if(!re.test(value)){
			return false;
		}
		return true;
	},UDP.Public.getmessage("validate.phone","请输入正确的手机号或电话号!"));

	/**
	 * 身份证号验证
	 * */
	jQuery.validator.addMethod("identityCard",function(value,element,paramsvalue){
		var re = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
		if(!re.test(value)){
			return false;
		}
		return true;
	},UDP.Public.getmessage("validate.identityCard","错误的身份证号!"));

	/**
	 * 验证字符串两端是否有空格
	 **/
	jQuery.validator.addMethod("bothTrim",function(value,element,paramsvalue){
		var reg=/^[\s　]|[ ]$/gi;
		if(reg.test(value)){
			return false;
		}
		return true;
	},UDP.Public.getmessage("validate.bothTrim","两端不能有空格!"));

	/**
	 * 开始时间、结束时间
	 * */
	jQuery.validator.addMethod("compareDate",function(value,element,paramsvalue){
		var startDate = jQuery(paramsvalue).val();
		var date1 = new Date(Date.parse(startDate.replace("-", "/")));
		var date2 = new Date(Date.parse(value.replace("-", "/")));
		return date1 < date2;
	},UDP.Public.getmessage("validate.compareDate","结束时间必须大于开始时间!"));

	/**
	 * 插件内部默认的提示信息实现多语言*/
	jQuery.extend(jQuery.validator.messages, {
		required:UDP.Public.getmessage("validate.required","必选字段"),
		remote: UDP.Public.getmessage("validate.remote","请修正该字段"),
		email: UDP.Public.getmessage("validate.email","请输入正确格式的电子邮件"),
		url: UDP.Public.getmessage("validate.url","请输入合法的网址"),
		date: UDP.Public.getmessage("validate.date","请输入合法的日期"),
		dateISO:UDP.Public.getmessage("validate.dateISO","请输入合法的日期 (ISO).") ,
		number: UDP.Public.getmessage("validate.number","请输入合法的数字"),
		digits:UDP.Public.getmessage("validate.digits","只能输入整数"),
		creditcard:UDP.Public.getmessage("validate.creditcard","请输入合法的信用卡号"),
		equalTo:UDP.Public.getmessage("validate.equalTo","请再次输入相同的值"),
		accept: UDP.Public.getmessage("validate.accept","请输入拥有合法后缀名的字符串"),
		maxlength: jQuery.validator.format(UDP.Public.getmessage("validate.maxlength","请输入一个 长度最多是 {0} 的字符串")),
		minlength: jQuery.validator.format(UDP.Public.getmessage("validate.maxlength","请输入一个 长度最少是 {0} 的字符串")),
		rangelength: jQuery.validator.format(UDP.Public.getmessage("validate.rangelength","请输入 一个长度介于 {0} 和 {1} 之间的字符串")),
		range: jQuery.validator.format(UDP.Public.getmessage("validate.range","请输入一个介于 {0} 和 {1} 之间的值")),
		max: jQuery.validator.format(UDP.Public.getmessage("validate.max","请输入一个最大为{0} 的值")),
		min: jQuery.validator.format(UDP.Public.getmessage("validate.min","请输入一个最小为{0} 的值"))
	});
});
;(function(win) {
	var validate = {
		"fileType":"plug",
		"filename":"validate"
	};
	win["UDP"].Public.PlugList(validate);
})(window);