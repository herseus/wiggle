// $(document).ready(function(){ 
// alert("您好，欢迎来到Jquery！"); 
// }) 

init();


function init() {

	// document.getElementById("btn_search").addEventListener('click', function(){
	// 	alert("Searching!");
	// }, false);

	// $("#btn_search").click(function() {



    // Fetch all product info
    // This function uses AJAX, which is asynchronous, so binding btn_query_product_detail must be in the asynchronous callback.
	fetch_product_info();


    bind_btn_search_product();

    bind_btn_add_product();
	
}


// Fetch all product info
function fetch_product_info () {
    fetch_product_info ("");
}

// Fetch product info for specific sku
function fetch_product_info (sku) {

    $.ajax({
        type:"GET",
        url:"http://192.168.0.107:8080/wiggle/product/getProductInfo",
        contentType: "application/json; charset=utf-8",   
        data: {"sku": sku},  
        async : true,  
        dataType: "json", 
        crossDomain: true, 
        success: function(response) {     
            //返回的数据用data.d获取内容  
            var success = response.success;
            if (true == success) {
                var str = "";
                var data = response.data;
                for (i in data) {
                    str += "<tr>"
                    + "<td>" + (parseInt(i) + 1) + "</td>"
                    + "<td>" + data[i].sku + "</td>"
                    // <img src="http://www.baidu.com/img/baidu_logo.gif" /></p> 
                    + "<td>" + "<img src=" + "http://amzimage-1253899278.cossh.myqcloud.com/"+data[i].sku +".jpg" +" style='width:60px;height:60px;'/>" + "</td>"
                    + "<td>" + data[i].title + "</td>"
                    + "<td>" + data[i].colorName + "</td>"
                    + "<td>" + data[i].cost + "</td>" 
                    + "<td>" + data[i].weight + "</td>" 
                    + "<td>" + data[i].vendorName + "</td>"
                    + "<td>" + "<a href='" + data[i].vendorLink + "' target='_blank'><input type='button' value='查看供应商'></input></a>" + "</td>"
                    + "<td>" + "<input type='button' class='btn_query_detail' value='查看产品详情' name='" + data[i].sku + "'></input>" + "</td>" 
                    + "</tr>";
                }
                window.document.getElementById("product_body").innerHTML = str;

                // This binding must be placed here after the return of the data
                bind_btn_query_product_detail();
            }    

        },     
        error: function(err) {     
            alert(err);  
        }
    });
}

//-------------------------------------------------------------------------------------------
// Confirm purchase of products
function purchase_product() {
    tableToExcel('table_product');
   
}

var tableToExcel = (function() {  
    var uri = 'data:application/vnd.ms-excel;base64,',  
            template = '<html><head><meta charset="UTF-8"></head><body><table>{table}</table></body></html>',  
            base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) },  
            format = function(s, c) {  
                return s.replace(/{(\w+)}/g,  
                        function(m, p) { return c[p]; }) }  
    return function(table, name) {  
        if (!table.nodeType) table = document.getElementById(table)  
        var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}  
        window.location.href = uri + base64(format(template, ctx))  
    }  
})()
//-------------------------------------------------------------------------------------------







function bind_btn_search_product() {
    $("#btn_search_product").click(function() {
        fetch_product_info($("#search_term_input").val().toString());
        
    });
}

function bind_btn_add_product() {
    $("#btn_add_product").click(function() {
        window.open("add_product.html");
    });
}

function bind_btn_query_product_detail() {
    var query_detail_btns = document.getElementsByClassName("btn_query_detail");
    for (var cnt=0, size = query_detail_btns.length; cnt < size; cnt++) {
        query_detail_btns[cnt].onclick = function() {
            window.open("index_dashboard.html");
        }

    }
}

function bind_btn_add_purchase_plan() {
    $("#btn_purchase").click(function() {
        purchase_product();
    });
}
