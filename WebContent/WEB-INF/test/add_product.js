init();

bind();


function init() {

}

function bind() {
	$("#btn_confirm_add").click(function() {
		add_one_product();
		var test = document.getElementById("main_image").getElementsByTagName("input")[0]
		var i = 1;
	});

	
}

function add_one_product() {
	$.ajax({
        type:"POST",
        url:"http://192.168.0.107:8080/wiggle/product/addProductInfo",
        contentType: "application/json; charset=utf-8",   
        data: {"sku": document.getElementById("sku").getElementsByTagName("input")[0].value,
        		"title": document.getElementById("title").getElementsByTagName("input")[0].value,
				"colorName": document.getElementById("color_name").getElementsByTagName("input")[0].value,
				"cost": document.getElementById("cost").getElementsByTagName("input")[0].value,
				"weight": document.getElementById("weight").getElementsByTagName("input")[0].value,
				"vendorName": document.getElementById("vendor_name").getElementsByTagName("input")[0].value,
				"vendorLink": document.getElementById("vendor_link").getElementsByTagName("input")[0].value

    		},  
        async : true,  
        dataType: "json", 
        crossDomain: true, 
        success: function(response) {     
            //返回的数据用data.d获取内容  
            var success = response.success;
            if (true == success) {
            	alert("添加成功！回到首页");
                window.location.href = "index.html";
                
            }    

        },     
        error: function(err) {     
            alert(err);  
        }
    });
}